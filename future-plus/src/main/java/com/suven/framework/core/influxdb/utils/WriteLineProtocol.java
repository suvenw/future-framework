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
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.time.Instant;

public class WriteLineProtocol {

    private static char[] token = "ltcTyWIt5Ltegv6CesVdFv_gocvi1PPhfjDKWWzW3TSWrrRc1xN7hqhAnwkldbuj6vdTaQAPjrF6SgfcQ9abGw==".toCharArray();
    private static String org = "admin";
    private static String bucket = "my-bucket";

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("https://influxdb.sixeco.com/", token, org, bucket);

        //
        // Write data
        //
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        //
        // Write by LineProtocol
        //
        String record = "temperature,location=north value=7671.0";

        writeApi.writeRecord(WritePrecision.NS, record);

        //Point
        Point point = Point.measurement("temperature")
                .addTag("location", "west")
                .addField("value", 4375D)
                .addField("add", 5275D)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        writeApi.writePoint(point);

        influxDBClient.close();
    }
}