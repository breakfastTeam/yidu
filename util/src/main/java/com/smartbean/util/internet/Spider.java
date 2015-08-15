package com.smartbean.util.internet;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015/6/23.
 */
public class Spider {
    private String rootUrl = "http://weixin.sogou.com/";

    /**
     * 查询订阅号
     * 数组内容：logo、openId、名称、账号、功能简介、认证公司
     * *
     */
    public List<String[]> searchWechatFromSogou(String keyword) {
        List<String[]> results = Lists.newArrayList();
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            String url = rootUrl.concat("weixin?type=1&query=").concat(keyword);
            String pageXml = this.getPageContentUseWebClient(url);

            Document document = Jsoup.parse(pageXml, rootUrl);
            Elements resultWrapper = document.getElementsByClass("results");
            Elements resultsEl = resultWrapper.get(0).getElementsByClass("_item");
            for (Element el : resultsEl) {
                String[] result = new String[6];
                String logo = el.getElementsByClass("img-box").get(0).getElementsByTag("img").get(0).attr("src");
                String openId = el.attr("href").split("=")[1];
                String name = el.getElementsByClass("txt-box").get(0).getElementsByTag("h3").text().trim();
                String wechatAccount = el.getElementsByClass("txt-box").get(0).getElementsByTag("h4").text().split("：")[0];
                Elements intros = el.getElementsByClass("txt-box").get(0).getElementsByTag("p");
                result[0] = logo;
                result[1] = openId;
                result[2] = name.replaceAll(" ","");
                result[3] = wechatAccount;
                if (intros.size() > 0) {
                    result[4] = intros.get(0).getElementsByClass("sp-txt").get(0).text().replaceAll(" ", "");//功能介绍
                    if (intros.size() >= 2) {
                        String infos = intros.get(1).text();
                        String title = infos.substring(0, 4);
                        infos = infos.substring(5, infos.length());

                        if ("微信认证".equals(title)) {
                            result[5] = infos;//认证公司
                        }
                    }
                }

                results.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 搜索文章
     * 数组内容：logo、微信号的openId、微信号名称、文章标题、文章简介、详情链接
     * *
     */
    public List<String[]> searchArticleFromSogou(String keyword) {
        List<String[]> results = Lists.newArrayList();
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            String url = rootUrl.concat("weixin?type=2&query=").concat(keyword);
            String pageXml = this.getPageContent(url);

            Document document = Jsoup.parse(pageXml, rootUrl);
            Elements resultWrapper = document.getElementsByClass("results");
            Elements resultsEl = resultWrapper.get(0).getElementsByClass("wx-rb");
            for (Element el : resultsEl) {
                String[] result = new String[6];
                String logo = el.getElementsByClass("img_box2").get(0).getElementsByTag("img").get(0).attr("src");
                String openId = el.getElementsByClass("txt-box").get(0).getElementsByClass("s-p").get(0).getElementsByTag("a").get(0).attr("i");
                String name = el.getElementsByClass("txt-box").get(0).getElementsByClass("s-p").get(0).getElementsByTag("a").get(0).attr("title");
                String title = el.getElementsByClass("txt-box").get(0).getElementsByTag("h4").text();
                String briefIntro = el.getElementsByClass("txt-box").get(0).getElementsByTag("p").text();
                String detailUrl = el.getElementsByClass("txt-box").get(0).getElementsByTag("h4").get(0).getElementsByTag("a").get(0).attr("href");
                result[0] = logo;
                result[1] = openId;
                result[2] = name;
                result[3] = title.replaceAll(" ","");
                result[4] = briefIntro.replaceAll(" ","");
                result[5] = detailUrl;

                results.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    /**
     * 根据订阅号openId获取订阅号的最新文章
     * 数组内容：logo、标题、简介、时间、详情链接, openId
     */
    public List<String[]> getWechatLastArticlesFromSogou(String openId) {
        String url = rootUrl.concat("gzh?openid=").concat(openId);
        List<String[]> results = Lists.newArrayList();
        try {
            String pageXml = this.getPageContentUseWebClient(url);
            Document document = Jsoup.parse(pageXml, rootUrl);
            Element resultWrapper = document.getElementById("wxbox");
            Elements resultsEl = resultWrapper.getElementsByClass("wx-rb");
            for (Element el : resultsEl) {
                String[] article = new String[6];
                String logo = el.getElementsByClass("img_box2").get(0).getElementsByTag("img").get(0).attr("src");
                String title = el.getElementsByClass("txt-box").get(0).getElementsByTag("a").get(0).text();
                String intro = el.getElementsByClass("txt-box").get(0).getElementsByTag("p").get(0).text();
                String time = el.getElementsByClass("txt-box").get(0).getElementsByTag("p").get(1).text();
                String detailUrl = el.getElementsByClass("txt-box").get(0).getElementsByTag("a").get(0).attr("href");
                article[0] = logo;
                article[1] = title.replaceAll(" ","");
                article[2] = intro.replaceAll(" ","");
                article[3] = time;
                article[4] = detailUrl;
                article[5] = openId;
                results.add(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 根据订阅号openId微信号详情信息
     * 数组内容：logo、名称、账号、简介、认证公司
     */
    public String[] getWechatFromSogou(String openId) {
        String url = rootUrl.concat("gzh?openid=").concat(openId);
        String[] wechat = new String[5];
        try {
            String pageXml = this.getPageContentUseWebClient(url);
            Document document = Jsoup.parse(pageXml, rootUrl);
            Elements resultWrapper = document.getElementsByClass("_item");

            String logo = resultWrapper.get(0).getElementsByClass("img-box").get(0).getElementsByTag("img").get(0).attr("src");
            String name = resultWrapper.get(0).getElementsByClass("txt-box").get(0).getElementById("weixinname").text();
            String accountTemp = resultWrapper.get(0).getElementsByClass("txt-box").get(0).getElementsByTag("h4").get(0).text();
            String account = accountTemp.substring(4, accountTemp.length()-1);
            Elements infos = resultWrapper.get(0).getElementsByClass("txt-box").get(0).getElementsByClass("sp-txt");
            String intro = resultWrapper.get(0).getElementsByClass("txt-box").get(0).getElementsByClass("sp-txt").get(0).text();
            String company = "";
            if (infos.size() > 1) {
                company = resultWrapper.get(0).getElementsByClass("txt-box").get(0).getElementsByClass("sp-txt").get(1).text();
            }

            wechat[0] = logo;
            wechat[1] = name.replaceAll(" ","");
            wechat[2] = account;
            wechat[3] = intro.replaceAll(" ","");
            wechat[4] = company.replaceAll(" ","");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wechat;
    }

    /**
     * 获取搜狗热门的文章
     * 数组内容：logo、标题、简介、详情链接、微信openId
     */
    public List<String[]> getSogouHotArticles() {
        List<String[]> results = Lists.newArrayList();
        try {
            String pageXml = this.getPageContent(rootUrl);
            Document document = Jsoup.parse(pageXml, rootUrl);
            Element resultWrapper = document.getElementById("pc_0_d");
            Elements resultsEl = resultWrapper.getElementsByTag("li");
            for (Element el : resultsEl) {
                String[] article = new String[5];
                String logo = el.getElementsByClass("wx-img-box").get(0).getElementsByTag("img").get(0).attr("src");
                String title = el.getElementsByClass("wx-news-info2").get(0).getElementsByTag("h4").get(0).text();
                String intro = el.getElementsByClass("wx-news-info2").get(0).getElementsByTag("a").get(1).text();
                String detailUrl = el.getElementsByClass("wx-news-info2").get(0).getElementsByTag("a").get(1).attr("href");
                String openId = el.getElementsByClass("pos-wxrw").get(0).getElementsByTag("a").get(0).attr("href").split("=")[1];
                article[0] = logo;
                article[1] = title.replaceAll(" ","");
                article[2] = intro.replaceAll(" ","");
                article[3] = detailUrl;
                article[4] = openId;
                results.add(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 获取文章详情
     * *
     */
    public String getArticle(String url) {
        String article = new String();
        String sogouStore = "http://img01.store.sogou.com/net/a/04/link?appid=100520031&w=711&url=";
        try {
            String pageXml = this.getPageContent(url);

            Document document = Jsoup.parse(pageXml, rootUrl);
            Element resultWrapper = document.getElementsByTag("html").get(0);
            resultWrapper.getElementsByTag("script").remove();
            resultWrapper.getElementById("activity-name").remove();
            resultWrapper.getElementsByClass("rich_media_meta_list").remove();
            Elements medias = resultWrapper.getElementsByAttribute("data-src");
            for(Element media : medias){
                if(media.hasAttr("allowfullscreen")){
                    media.attr("src", media.attr("data-src"));
                    media.removeAttr("data-src");
                }else{
                    media.attr("src", sogouStore + media.attr("data-src"));
                    media.removeAttr("data-src");
                }
            }
            article = resultWrapper.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }


    private String getPageContentUseWebClient(String url) {
        String pageXml = new String();
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(true); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(url);
            pageXml = page.asXml(); //以xml的形式获取响应文本
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageXml;
    }
private String getPageContent(String url) {
    StringBuffer data = new StringBuffer();
    URLConnection connection = null;
    try {
        connection = new URL(url).openConnection();
        connection.connect();

        InputStream fin = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(fin));
        String temp = null;
        byte[] bytes = new byte[4096];
        int size = 0;
        while ((size = fin.read(bytes)) > 0) {
            temp = new String(bytes, 0, size, "UTF-8");
            data.append(temp);
        }


    } catch (Exception e) {
        e.printStackTrace();
    }

    return data.toString();
}
    public static void main(String args[]) throws Exception {
        Spider s = new Spider();
//        s.searchWechatFromSogou("36");
//        s.getWechatLastArticlesFromSogou("oIWsFt5S-CcRNFhBp0VHuY7C32v4");
//        s.getArticle("http://mp.weixin.qq.com/s?__biz=MzAwMTAzMzY3Mg==&mid=212656083&idx=2&sn=b4d5bbadb39cc3bd8ddfe555b162ea4f&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
//        s.getHotSubject();
//        s.searchArticle("红包");
//        s.getSubject();
//        s.getWechatFromSogou("oIWsFtw1fsjn6vkpooWGkgbdLsU4");
//        s.getSogouHotArticles();
//        s.getArticle("http://mp.weixin.qq.com/s?__biz=MjM5NTAyODc2MA==&amp;mid=228832329&amp;idx=6&amp;sn=3286b7673999ac7922c5e87c9c972935#rd");
//        s.grabData("http://weixin.sogou.com/");

    }

}
