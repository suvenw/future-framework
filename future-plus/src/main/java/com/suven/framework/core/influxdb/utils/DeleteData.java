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

import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.exceptions.InfluxException;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Pavlina Rolincova (rolincova@github) (29/10/2019).
 */
public class DeleteData {

    private static char[] token = "ltcTyWIt5Ltegv6CesVdFv_gocvi1PPhfjDKWWzW3TSWrrRc1xN7hqhAnwkldbuj6vdTaQAPjrF6SgfcQ9abGw==".toCharArray();

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("https://influxdb.sixeco.com/", token);

        DeleteApi deleteApi = influxDBClient.getDeleteApi();

        try {

            OffsetDateTime start = OffsetDateTime.now().minus(3, ChronoUnit.HOURS);
            OffsetDateTime stop = OffsetDateTime.now();

            deleteApi.delete(start, stop, "location=north", "my-bucket", "admin");

        } catch (InfluxException ie) {
            System.out.println("InfluxException: " + ie);
        }

        influxDBClient.close();
    }
}
