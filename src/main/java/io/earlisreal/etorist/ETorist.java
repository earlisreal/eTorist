package io.earlisreal.etorist;

import java.io.IOException;

public class ETorist {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to eTorist!");

        MoneyExperimentPHScraper scraper = new MoneyExperimentPHScraper();
        if (args.length > 0) {
            scraper.scrape(args[0]);
        }
        else {
            scraper.scrape();
        }

    }

}
