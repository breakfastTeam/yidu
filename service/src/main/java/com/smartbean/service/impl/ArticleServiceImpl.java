package com.smartbean.service.impl;

import com.github.sd4324530.fastweixin.api.CustomAPI;
import com.github.sd4324530.fastweixin.message.*;
import com.google.common.collect.Lists;
import com.smartbean.common.*;
import com.smartbean.entity.*;
import com.smartbean.entity.Article;
import com.smartbean.repository.ArticleRepository;
import com.smartbean.repository.CustomerRepository;
import com.smartbean.service.*;
import com.smartbean.util.internet.Spider;
import com.smartbean.util.internet.SpiderTask;
import com.smartbean.util.weixin.WeixinConfig;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2015/6/28.
 */
@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Value("#{configProperties['host']}")
    private String host;
    @Value("#{configProperties['subscribeContentUrl']}")
    private String subscribeContentUrl;
    @Value("#{configProperties['appid']}")
    private String appId;
    @Value("#{configProperties['appsecret']}")
    private String appSecret;
    @Value("#{configProperties['articleDetailUrl']}")
    private String articleDetailUrl;


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private ReadLogService readLogService;

    @Autowired
    private PushLogService pushLogService;


    @PersistenceContext
    private EntityManager em;

    @Override
    public Article findOne(String id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> save(List<Article> articles) {
        return articleRepository.save(articles);
    }

    @Override
    public Article saveOrUpdate(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Page<Article> getBySubjectName(Pageable page, String subjectName) {
        String subjectId = subjectService.getTodaySubjectId(subjectName);
        return this.getBySubjectId(page, subjectId);
    }

    @Override
    public Page<Article> getBySubjectId(Pageable page, String subjectId) {
        Article article = new Article();
        article.setStatus(ArticleStatus.PUBLISHED.toString());
        article.setSubjectId(subjectId);
        return this.findAll(page, article);
    }

    @Override
    public Page<Article> getByWechat(Pageable page, String wechatId) {
        Article article = new Article();
        article.setStatus(ArticleStatus.PUBLISHED.toString());
        article.setWechatId(wechatId);
        return this.findAll(page, article);
    }


    @Override
    public Page<Article> findAll(Pageable page, final Article article) {
        return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringUtils.isNotBlank(article.getWechatId())) {
                    expressions.add(cb.equal(root.get("wechatId").as(String.class), article.getWechatId()));
                }
                if (StringUtils.isNotBlank(article.getSubjectId())) {
                    expressions.add(cb.equal(root.get("subjectId").as(String.class), article.getSubjectId()));
                }
                if (StringUtils.isNotBlank(article.getDetailUrl())) {
                    expressions.add(cb.equal(root.get("detailUrl").as(String.class), article.getDetailUrl()));
                }
                if (StringUtils.isNotBlank(article.getStatus())) {
                    expressions.add(cb.equal(root.get("status").as(String.class), article.getStatus()));
                }
                return predicate;
            }
        }, page);
    }

    @Override
    public Article getByDetailUrl(String url) {
        Article article = new Article();
        article.setDetailUrl(url);
        List<Article> articles = this.findAll(article);
        if (articles != null && articles.size() > 0) {
            return articles.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Page<Article> getByCustomer(Pageable page, String customerId) {
        List<String> wechatIds = subscribeService.findWechatIdsByCustomerId(customerId);
        return articleRepository.findByWechatIdInAndStatus(wechatIds, ArticleStatus.PUBLISHED.toString(), page);
    }

    @Override
    public Article publish(String articleId) {
        Article article = articleRepository.findOne(articleId);
        article.setStatus(ArticleStatus.PUBLISHED.toString());
        articleRepository.save(article);
        return article;
    }

    @Override
    public Article unpublish(String articleId) {
        Article article = articleRepository.findOne(articleId);
        article.setStatus(ArticleStatus.UNPUBLISHED.toString());
        articleRepository.save(article);
        return article;
    }

    @Override
    public Article changeSubject(String articleId, String subjectId) {
        Article article = articleRepository.findOne(articleId);
        article.setSubjectId(subjectId);
        articleRepository.save(article);
        return article;
    }


    private List<Article> findAll(final Article article) {
        return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();

                if (StringUtils.isNotBlank(article.getDetailUrl())) {
                    expressions.add(cb.equal(root.get("detailUrl").as(String.class), article.getDetailUrl()));
                }
                return predicate;
            }
        });
    }

    @Override
    public void grabHotArticleFromSogou() {
        Spider spider = new Spider();
        List<Article> articles = Lists.newArrayList();
        List<String[]> webArticles = spider.getSogouHotArticles();
        for (String[] webArticle : webArticles) {
            Article article = this.getByDetailUrl(webArticle[3]);
            if (article == null) {
                article = new Article();
                article.setLogo(webArticle[0]);
                article.setTitle(webArticle[1]);
                article.setBriefIntro(webArticle[2]);
                article.setDetailUrl(webArticle[3]);
                article.setOpenId(webArticle[4]);
                article.setStatus(ArticleStatus.UNPUBLISHED.toString());
                article.setReadTimes(0);
                String content = spider.getArticle(webArticle[3]);
                article.setContent(content);
                article.setCreateTime(DateTime.now());
                articles.add(article);
            }
        }
        articleRepository.save(articles);

    }

    public void pushNewHotArticles() {
        List<Customer> customers = this.getAllCustomerIds();
        WeixinConfig weixinConfig = new WeixinConfig();
        CustomAPI customerAPI = weixinConfig.getCustomAPI(appId, appSecret);

        for (Customer customer : customers) {
            Page<Article> page = this.getUnpushedAndUnreadArticles(new PageRequest(0, PageConfig.PAGE_SIZE, new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"))), customer.getId());
            List<PushLog> pushLogs = Lists.newArrayList();
            List<com.github.sd4324530.fastweixin.message.Article> wexinArticles = Lists.newArrayList();
            for (int i = 0; i < page.getContent().size(); i++) {
                if (i < 5) {
                    com.github.sd4324530.fastweixin.message.Article weixinArticle = new com.github.sd4324530.fastweixin.message.Article();
                    weixinArticle.setPicUrl(page.getContent().get(i).getLogo());
                    weixinArticle.setTitle(page.getContent().get(i).getTitle());
                    weixinArticle.setDescription(page.getContent().get(i).getBriefIntro());
                    weixinArticle.setUrl(articleDetailUrl + page.getContent().get(i).getId());
                    wexinArticles.add(weixinArticle);
                } else if (i == 6) {
                    com.github.sd4324530.fastweixin.message.Article weixinArticle = new com.github.sd4324530.fastweixin.message.Article();
                    weixinArticle.setPicUrl(host + "/upload/more.jpg");
                    weixinArticle.setTitle(Constants.readMore);
                    weixinArticle.setUrl(subscribeContentUrl);
                    wexinArticles.add(weixinArticle);
                }
                PushLog pushLog = new PushLog();
                pushLog.setCreateTime(DateTime.now());
                pushLog.setCustomerId(customer.getId());
                pushLog.setTypeId(page.getContent().get(i).getId());
                pushLog.setType(PushType.ARTICLE.toString());
                pushLogs.add(pushLog);
            }
            NewsMsg newsMsg = new NewsMsg(wexinArticles);
            customerAPI.sendCustomMessage(customer.getOpenId(), newsMsg);

            pushLogService.save(pushLogs);
        }
    }

    public void pushWechatArticles() {
        List<Customer> customers = this.getAllCustomerIds();
        WeixinConfig weixinConfig = new WeixinConfig();
        CustomAPI customerAPI = weixinConfig.getCustomAPI(appId, appSecret);

        for (Customer customer : customers) {
            Page<Article> page = this.getUnpushedAndUnreadWechatArticles(new PageRequest(0, PageConfig.PAGE_SIZE, new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"))), customer.getId());
            List<com.github.sd4324530.fastweixin.message.Article> wexinArticles = Lists.newArrayList();
            List<PushLog> pushLogs = Lists.newArrayList();
            if (page.hasContent()) {
                for (int i = 0; i < page.getContent().size(); i++) {
                    if (i <= 5) {
                        com.github.sd4324530.fastweixin.message.Article weixinArticle = new com.github.sd4324530.fastweixin.message.Article();
                        weixinArticle.setPicUrl(page.getContent().get(i).getLogo());
                        weixinArticle.setTitle(page.getContent().get(i).getTitle());
                        weixinArticle.setDescription(page.getContent().get(i).getBriefIntro());
                        weixinArticle.setUrl(articleDetailUrl + page.getContent().get(i).getId());
                        wexinArticles.add(weixinArticle);
                    } else if (i == 6) {
                        com.github.sd4324530.fastweixin.message.Article weixinArticle = new com.github.sd4324530.fastweixin.message.Article();
                        weixinArticle.setPicUrl(host + "/upload/more.jpg");
                        weixinArticle.setTitle(Constants.readMore);
                        weixinArticle.setUrl(subscribeContentUrl);
                        wexinArticles.add(weixinArticle);
                    }

                    PushLog pushLog = new PushLog();
                    pushLog.setCreateTime(DateTime.now());
                    pushLog.setCustomerId(customer.getId());
                    pushLog.setTypeId(page.getContent().get(i).getId());
                    pushLog.setType(PushType.ARTICLE.toString());
                    pushLogs.add(pushLog);
                }
                NewsMsg newsMsg = new NewsMsg(wexinArticles);
                customerAPI.sendCustomMessage(customer.getOpenId(), newsMsg);

                pushLogService.save(pushLogs);
            }
        }

    }

//    @Override
//    public void grabWechatArticle() {
//        List<Wechat> wechats = wechatService.findAll();
//        List<Future> futures = Lists.newArrayList();
//        Spider spider = new Spider();
//        if (wechats != null && wechats.size() > 0) {
//            int size = 100;
//            int count = wechats.size() / size;
//            ExecutorService pool = Executors.newFixedThreadPool(100);
//            if (count == 0) {
//                List<String> openIds = Lists.newArrayList();
//                for (Wechat wechat : wechats) {
//                    openIds.add(wechat.getOpenId());
//                }
//                SpiderTask c = new SpiderTask(openIds);
//                Future f = pool.submit(c);
//                futures.add(f);
//            } else {
//                for (int i = 0; i <= count; i++) {
//                    List<String> openIds = Lists.newArrayList();
//                    List<Wechat> innerWechats;
//                    if (i == count) {
//                        innerWechats = wechats.subList(i * size, wechats.size());
//                    } else {
//                        innerWechats = wechats.subList(i * size, (i + 1) * size);
//                    }
//                    for (Wechat wechat : innerWechats) {
//                        openIds.add(wechat.getOpenId());
//                    }
//                    SpiderTask c = new SpiderTask(openIds);
//                    Future f = pool.submit(c);
//
////                    futures.add(f);
//                }
//            }
//
////            Spider spider = new Spider();
//            for (Future f : futures) {
////                try {
////                    List<List<String[]>> results = (List<List<String[]>>) f.get();
////                    for (List<String[]> result : results) {
////                        List<Article> articles = Lists.newArrayList();
////                        for (String[] webArticle : result) {
////                            List<Article> temp = articleRepository.findByDetailUrl(webArticle[4]);
////                            if (temp==null || temp.size()<=0) {
////                                Article article = new Article();
////                                article.setLogo(webArticle[0]);
////                                article.setTitle(webArticle[1]);
////                                article.setBriefIntro(webArticle[2]);
////                                article.setDetailUrl(webArticle[4]);
////                                article.setOpenId(webArticle[5]);
////                                Wechat wechat = wechatService.findByOpenId(webArticle[5]);
////                                article.setWechatId(wechat.getId());
////                                article.setStatus(ArticleStatus.PUBLISHED.toString());
////                                article.setReadTimes(0);
////                                String content = spider.getArticle(webArticle[4]);
////                                article.setContent(content);
////                                article.setCreateTime(DateTime.now());
////                                articles.add(article);
////                            }
////                        }
////                        articleRepository.save(articles);
////
////                    }
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (ExecutionException e) {
////                    e.printStackTrace();
////                }
//            }
//
//            pool.shutdown();
//        }
//
//    }


    @Override
    public void grabWechatArticle() {
        List<Wechat> wechats = wechatService.findAll();
        try {
            Spider spider = new Spider();
            int size = wechats.size();
            ExecutorService pool = Executors.newFixedThreadPool(size);
            for (Wechat wechat : wechats) {
                SpiderTask c = new SpiderTask(wechat.getOpenId());
                Future f = pool.submit(c);
                List<String[]> result = (List<String[]>) f.get();
                List<Article> articles = Lists.newArrayList();
                for (String[] webArticle : result) {
                    List<Article> temp = articleRepository.findByDetailUrl(webArticle[4]);
                    if (temp == null || temp.size() <= 0) {
                        Article article = new Article();
                        article.setLogo(webArticle[0]);
                        article.setTitle(webArticle[1]);
                        article.setBriefIntro(webArticle[2]);
                        article.setDetailUrl(webArticle[4]);
                        article.setOpenId(webArticle[5]);
                        article.setWechatId(wechat.getId());
                        article.setStatus(ArticleStatus.PUBLISHED.toString());
                        article.setReadTimes(0);
                        String content = spider.getArticle(webArticle[4]);
                        article.setContent(content);
                        article.setCreateTime(DateTime.now());
                        articles.add(article);
                    }
                }
                this.save(articles);
            }
            pool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<Article> grabWechatLastArticle(String openId, String wechatId) {
        Spider spider = new Spider();
        List<Article> articles = Lists.newArrayList();
        List<String[]> webArticles = spider.getWechatLastArticlesFromSogou(openId);
        for (String[] webArticle : webArticles) {
            Article article = new Article();
            article.setLogo(webArticle[0]);
            article.setTitle(webArticle[1]);
            article.setBriefIntro(webArticle[2]);
            article.setDetailUrl(webArticle[4]);
            article.setOpenId(webArticle[5]);
            article.setWechatId(wechatId);
            article.setStatus(ArticleStatus.PUBLISHED.toString());
            article.setReadTimes(0);
            String content = spider.getArticle(webArticle[4]);
            article.setContent(content);
            article.setCreateTime(DateTime.now());
            articles.add(article);
        }
        return articles;
    }


    /**
     * 获取系统中所有客户的openId和customerId;
     * *
     */
    private List<Customer> getAllCustomerIds() {
        List<Customer> customers = Lists.newArrayList();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Customer> customerRoot = cq.from(Customer.class);

        cq.select(cb.tuple(customerRoot.get(customerRoot.getModel().getSingularAttribute("id", String.class))
                , customerRoot.get(customerRoot.getModel().getSingularAttribute("openId", String.class))));
        cq.where(
                cb.equal(customerRoot.get(customerRoot.getModel().getSingularAttribute("status", String.class)), CustomerStatus.FOLLOW.toString())
        );

        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();

        for (Tuple tuple : result) {
            Customer customer = new Customer();
            customer.setId((String) tuple.get(0));
            customer.setOpenId((String) tuple.get(1));
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Page<Article> getUnreadArticles(Pageable page, String customerId) {
        List<String> readArticlesId = readLogService.getReadArticlesId(customerId);
        DateTime createTime = customerRepository.findOne(customerId).getCreateTime();
        return articleRepository.findByIdNotInAndStatusAndCreateTimeGreaterThanEqual(readArticlesId, ArticleStatus.PUBLISHED.toString(), createTime, page);
    }

    @Override
    public Page<Article> getUnreadWechatArticles(Pageable page, String customerId) {
        List<String> readArticlesId = readLogService.getReadArticlesId(customerId);
        List<String> wechatIds = subscribeService.getSubscribWechatId(customerId);
        DateTime createTime = customerRepository.findOne(customerId).getCreateTime();
        return articleRepository.findByIdNotInAndWechatIdInAndStatusAndCreateTimeGreaterThanEqual(readArticlesId, wechatIds, ArticleStatus.PUBLISHED.toString(), createTime, page);
    }

    @Override
    public Page<Article> getUnpushedArticles(Pageable page, String customerId) {
        List<String> pushedArticlesId = pushLogService.getPushedArticlesId(customerId);
        DateTime createTime = customerRepository.findOne(customerId).getCreateTime();
        return articleRepository.findByIdNotInAndStatusAndCreateTimeGreaterThanEqual(pushedArticlesId, ArticleStatus.PUBLISHED.toString(), createTime, page);
    }

    @Override
    public Page<Article> getUnpushedAndUnreadArticles(Pageable page, String customerId) {
        List<String> pushedArticlesId = pushLogService.getPushedArticlesId(customerId);
        List<String> readArticlesId = readLogService.getReadArticlesId(customerId);
        if (readArticlesId.size() == 0) {
            readArticlesId.add("");
        }
        if (pushedArticlesId.size() == 0) {
            pushedArticlesId.add("");
        }
        DateTime createTime = customerRepository.findOne(customerId).getCreateTime();
        return articleRepository.findByIdNotInAndIdNotInAndStatusAndCreateTimeGreaterThanEqual(pushedArticlesId, readArticlesId, ArticleStatus.PUBLISHED.toString(), createTime, page);
    }

    @Override
    public Page<Article> getUnpushedWechatArticles(Pageable page, String customerId) {
        List<String> pushedArticlesId = pushLogService.getPushedArticlesId(customerId);
        List<String> wechatIds = subscribeService.getSubscribWechatId(customerId);
        if (pushedArticlesId.size() == 0) {
            pushedArticlesId.add("");
        }
        if (wechatIds.size() == 0) {
            wechatIds.add("");
        }
        DateTime createTime = customerRepository.findOne(customerId).getCreateTime();
        return articleRepository.findByIdNotInAndWechatIdInAndStatusAndCreateTimeGreaterThanEqual(pushedArticlesId, wechatIds, ArticleStatus.PUBLISHED.toString(), createTime, page);
    }

    @Override
    public Page<Article> getUnpushedAndUnreadWechatArticles(Pageable page, String customerId) {
        List<String> pushedArticlesId = pushLogService.getPushedArticlesId(customerId);
        List<String> wechatIds = subscribeService.getSubscribWechatId(customerId);
        List<String> readArticlesId = readLogService.getReadArticlesId(customerId);
        if (pushedArticlesId.size() == 0) {
            pushedArticlesId.add("");
        }
        if (wechatIds.size() == 0) {
            wechatIds.add("");
        }
        if (readArticlesId.size() == 0) {
            readArticlesId.add("");
        }
        DateTime createTime = customerRepository.findOne(customerId).getCreateTime();
        return articleRepository.findByIdNotInAndIdNotInAndWechatIdInAndStatusAndCreateTimeGreaterThanEqual(pushedArticlesId, wechatIds, readArticlesId, ArticleStatus.PUBLISHED.toString(), createTime, page);
    }

    @Override
    public boolean hasUnreadArticles(String customerId) {
        List<String> readArticlesId = readLogService.getReadArticlesId(customerId);
        return articleRepository.findCountByIdNotInAndStatus(readArticlesId, ArticleStatus.PUBLISHED.toString()) > 0;
    }

    @Override
    public boolean hasUnreadWechatArticles(String customerId) {
        List<String> readArticlesId = readLogService.getReadArticlesId(customerId);
        List<String> wechatIds = subscribeService.getSubscribWechatId(customerId);

        if (wechatIds.size() == 0) {
            wechatIds.add("");
        }
        if (readArticlesId.size() == 0) {
            readArticlesId.add("");
        }
        return articleRepository.findCountByIdNotInAndWechatIdInAndStatus(readArticlesId, wechatIds, ArticleStatus.PUBLISHED.toString()) > 0;
    }
}
