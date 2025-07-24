/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.suven.framework.core.influxdb.utils;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;

public class RawQuery {

    private static char[] token = "38xmvoepfIhtkp3AfpBy7yrMeOdnAeXXa9vzOd4TIJ81o1zhoNhQExEffXMiVotHVjF1DHb92pL7ctLX0cE3lg==".toCharArray();
    private static String org = "admin";

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("https://influxdb.sixeco.com/", token, org);

        //
        // Query data
        //
        String flux = "from(bucket:\"my-bucket\") |> range(start: 0)";

        QueryApi queryApi = influxDBClient.getQueryApi();

        String csv = queryApi.queryRaw(flux);

        System.out.println("CSV response: " + csv);


        queryApi.queryRaw(flux, (cancellable, line) -> {

            //
            // The callback to consume a line of CSV response
            //
            // cancelable - object has the cancel method to stop asynchronous query
            //
            System.out.println("Response: " + line);
        });

        influxDBClient.close();
    }
}