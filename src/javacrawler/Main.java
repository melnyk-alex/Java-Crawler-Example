/*
 * Copyright (C) 2016 CodeFireUA <edu@codefire.com.ua>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package javacrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("./temp/");
        config.setMaxDepthOfCrawling(1);
        config.setMaxPagesToFetch(10);

        PageFetcher fetcher = new PageFetcher(config);
        RobotstxtServer robotstxtServer = new RobotstxtServer(new RobotstxtConfig(), fetcher);

        try {
            CrawlController controller = new CrawlController(config, fetcher, robotstxtServer);
            controller.addSeed("http://kneu.edu.ua");
            controller.start(PageCrawler.class, 10);
            List<Object> crawlersLocalData = controller.getCrawlersLocalData();

            for (Object data : crawlersLocalData) {
                System.out.println(data);
                if (data instanceof PageCrawler.LocalData) {
                    PageCrawler.LocalData localData = (PageCrawler.LocalData) data;

                    List<String> emails = localData.getEmails();
                    
                    for (String email : emails) {
                        System.out.println(email);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
