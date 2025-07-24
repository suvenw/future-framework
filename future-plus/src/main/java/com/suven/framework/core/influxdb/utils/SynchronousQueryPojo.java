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

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.suven.framework.core.influxdb.dsl.Flux;
import com.suven.framework.core.influxdb.dsl.functions.restriction.Restrictions;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SynchronousQueryPojo {

    private static char[] token = "ltcTyWIt5Ltegv6CesVdFv_gocvi1PPhfjDKWWzW3TSWrrRc1xN7hqhAnwkldbuj6vdTaQAPjrF6SgfcQ9abGw==".toCharArray();
    private static String org = "admin";

    public static void main(final String[] args) throws InterruptedException {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("https://influxdb.sixeco.com/", token, org);

        //
        // Query data
        //
//        String flux = "from(bucket:\"my-bucket\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"temperature\")";
//
        QueryApi queryApi = influxDBClient.getQueryApi();
//
//        //
//        // Map to POJO
//        //
//        List<Temperature> temperatures = queryApi.query(flux, Temperature.class);
//        for (Temperature temperature : temperatures) {
//            System.out.println(temperature.location + ": " + temperature.value + " at " + temperature.time);
//        }


//        String flux1 = "from(bucket:\"my-bucket\") |> range(start: 0)";

        //
        // Query data
        //
//        List<FluxTable> tables = queryApi.query(flux1);
//        for (FluxTable fluxTable : tables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//            for (FluxRecord fluxRecord : records) {
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//            }
//        }

        Restrictions restriction = Restrictions.and(
                Restrictions.tag("location").equal("west")
//                Restrictions.tag("_field").equal("add")
        );
        Flux flux = Flux.from("my-bucket")
                .range(-100L, ChronoUnit.DAYS)
                .filter(Restrictions.and(Restrictions.measurement().equal("temperature")).and())
                .withRestrictions(restriction)
                .aggregateWindow(3L,ChronoUnit.DAYS , "sum")
                .groupBy("add")
                .count("_value")
                .limit(10);

        //同步请求
        queryApi.query(flux.toString(), (cancellable, fluxRecord) -> {

            //
            // The callback to consume a FluxRecord.
            //
            // cancelable - object has the cancel method to stop asynchronous query
            //
            System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value")+ ": " + fluxRecord.getValueByKey("_field"));

        }, throwable -> {

            //
            // The callback to consume any error notification.
            //
            System.out.println("Error occurred: " + throwable.getMessage());

        }, () -> {

            //
            // The callback to consume a notification about successfully end of stream.
            //
            System.out.println("Query completed");

        });

        Thread.sleep(5_000);



        //
        // Query data
        //
//        List<FluxTable> tables = new ArrayList<>();
//        String da = "";
//        try {
//            tables = queryApi.query(flux.toString());
////            da = queryApi.queryRaw(flux.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        for (FluxTable fluxTable : tables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//            for (FluxRecord fluxRecord : records) {
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value") + ": " + fluxRecord.getValueByKey("_field"));
//            }
//        }

        influxDBClient.close();
    }

    @Measurement(name = "temperature")
    public static class Temperature {

        @Column(tag = true)
        String location;

        @Column
        Double value;

        @Column(timestamp = true)
        Instant time;
    }
}
