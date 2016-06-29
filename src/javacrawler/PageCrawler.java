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

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class PageCrawler extends WebCrawler {
    
    // TODO: Replace "email@server.com" with valid regular expression.
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\d{4}", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    private final LocalData localData;

    public PageCrawler() {
        this.localData = new LocalData();
    }

    @Override
    public boolean shouldVisit(Page page, WebURL url) {
        return true;
    }

    @Override
    public void visit(Page page) {
        System.out.println(page.getWebURL().toString());
        
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData parseData = (HtmlParseData) page.getParseData();
            String html = parseData.getHtml();
            
            Matcher matcher = EMAIL_PATTERN.matcher(html);
            
            while (matcher.find()) {
                localData.add(matcher.group());
            }
        }
    }

    @Override
    public Object getMyLocalData() {
        return this.localData;
    }

    /**
     * Store data from crawling.
     */
    public static class LocalData {

        private final List<String> emails;

        public LocalData() {
            this.emails = new ArrayList<>();
        }

        public List<String> getEmails() {
            return emails;
        }

        public boolean add(String e) {
            return emails.add(e);
        }
    }
}
