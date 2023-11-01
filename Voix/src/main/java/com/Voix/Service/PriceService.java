package com.Voix.Service;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.Voix.Dto.Price;

@Service
public class PriceService {
    public ArrayList<Price> getPricemelon() throws IOException {
        System.out.println("Service - getPricemelon() 호출");

        ArrayList<Price> priceList = new ArrayList<>();

        // Melon 정보 수집
        String melonPricePage = "https://www.melon.com/buy/pamphlet/all.htm";
        Document melonPriceDoc = Jsoup.connect(melonPricePage).get();
        Price melonPrice = new Price();
        String melonPriceName = melonPriceDoc.select("#conts > div.wrap_product > div.wrap_section02.streaming > div:nth-child(4) > div.product_titleArea > h4").text().substring(0, 14);
        melonPrice.setPricename(melonPriceName);
        String melonPriceSt = melonPriceDoc.select("#conts > div.wrap_product > div.wrap_section02.streaming > div:nth-child(4) > div.product_info > div > dl > dd > span").text();
        melonPrice.setStrprice(melonPriceSt);
        String melonImg = melonPriceDoc.select("#gnb > h1 > a > img").attr("src");
        melonPrice.setPageimg(melonImg);
        melonPrice.setPage("Melon");
        priceList.add(melonPrice);

        // Vibe 정보 수집
     //    String vibePricePage = "https://vibe.naver.com/membership/vibe";
     //   Document vibePriceDoc = Jsoup.connect(vibePricePage).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").get();
        Price vibePrice = new Price();
        String vibePriceName = "무제한 듣기";
        vibePrice.setPricename(vibePriceName);
        String vibePriceStr = "4,000원";
        vibePrice.setStrprice(vibePriceStr);
        String vibeImg = "https://vibe.naver.com/about/img/logo.png";
        vibePrice.setPageimg(vibeImg);
        vibePrice.setPage("Vibe");
        priceList.add(vibePrice);

        // Bugs 정보 수집
        String bugsPricePage = "https://music.bugs.co.kr/pay/public";
        Document bugsPriceDoc = Jsoup.connect(bugsPricePage).get();
        Price bugsPrice = new Price();
        String bugsPriceName = bugsPriceDoc.select("#container > section:nth-child(6) > div > ul > li:nth-child(1) > div > strong").text();
        bugsPrice.setPricename(bugsPriceName);
        String bugsPriceSt = bugsPriceDoc.select("#container > section:nth-child(6) > div > ul > li:nth-child(1) > ul > li:nth-child(1) > p > strong").text();
        bugsPrice.setStrprice(bugsPriceSt);
        String bugsImg = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F0RVHq%2FbtrbhZjXIC9%2F5peXUNCKSxIcpJkLTDerKK%2Fimg.jpg";
        bugsPrice.setPageimg(bugsImg);
        bugsPrice.setPage("Bugs");
        priceList.add(bugsPrice);

        // Genie 정보 수집
        String geniePricePage = "https://pay.genie.co.kr/buy/thirtyDays";
        Document geniePriceDoc = Jsoup.connect(geniePricePage).get();
        Price geniePrice = new Price();
        String geniePriceName = geniePriceDoc.select("#body-content > div.payment > div.products.list-month > div:nth-child(1) > h4").text();
        geniePrice.setPricename(geniePriceName);
        String geniePriceSt = geniePriceDoc.select("#body-content > div.payment > div.products.list-month > div:nth-child(1) > dl > dd:nth-child(4) > strong").text();
        geniePrice.setStrprice(geniePriceSt);
        String genieImg = "https://image.genie.co.kr/imageg/web/common/logo_genie_5.0.png";
        geniePrice.setPageimg(genieImg);
        geniePrice.setPage("Genie");
        priceList.add(geniePrice);
        
        System.out.println(priceList);

        return priceList;
    }
}

