package io.earlisreal.etorist;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MoneyExperimentPHScraper {

    public static final String URL = "https://docs.google.com/spreadsheets/u/0/d/e/2PACX-1vSQLtIx4-RhHRCxBBheNtyOGIEDfsGRBnkljJxn-7ElWxbStX1NfRBoWNaBvaOwM6MSq1s58YwLrQ60/pubhtml/sheet?headers=false&gid=0";

    public void scrape() throws IOException {
        scrape(".");
    }

    public void scrape(String path) throws IOException {
        System.out.println("Scraping Money Experiment PH eToro Stock List...");
        Path directory = Paths.get(path);
        System.out.println("Saving stock lists to: " + directory.toAbsolutePath());

        Document document = Jsoup.connect(URL).get();
        var columns = document.select("tr > td:nth-child(3)");

        System.out.println("Parsing Tickers");
        List<String> stocks = new ArrayList<>();
        for (var column : columns) {
            String code = column.text();
            if (code.isEmpty() || code.equals("Ticker")) continue;

            if (code.contains(".")) code = code.substring(0, code.indexOf("."));
            stocks.add(code);
        }

        int cnt = 1;
        StringBuilder output = new StringBuilder();
        System.out.println("Stocks size: " + stocks.size());
        for (int i = 0; i < stocks.size(); ++i) {
            if (i > 0 && i % 1000 == 0) {
                Files.write(directory.resolve("etoro-" + cnt + ".txt"), List.of(output.toString()));
                output = new StringBuilder();
                ++cnt;
            }
            if (output.length() > 0) output.append(",");
            output.append(stocks.get(i));
        }
        if (output.length() > 0) {
            Files.write(directory.resolve("etoro-" + cnt + ".txt"), List.of(output.toString()));
        }

        System.out.println("Done");
    }

}
