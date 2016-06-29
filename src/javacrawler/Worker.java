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

import edu.uci.ics.crawler4j.crawler.CrawlController;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class Worker implements Runnable {

    private WorkerListener listener;
    private CrawlController crawlController;

    public Worker(CrawlController crawlController) {
        this.crawlController = crawlController;
    }

    public void setListener(WorkerListener listener) {
        this.listener = listener;
    }

    public CrawlController getCrawlController() {
        return crawlController;
    }

    @Override
    public void run() {
        crawlController.start(PageCrawler.class, 10);
        
        List<PageCrawler.LocalData> datas = new ArrayList<>();

        for (Object data : crawlController.getCrawlersLocalData()) {
            if (data instanceof PageCrawler.LocalData) {
                datas.add((PageCrawler.LocalData) data);
            }
        }
        
        if (listener != null) {
            listener.scanComplete(datas);
        }
    }

}
