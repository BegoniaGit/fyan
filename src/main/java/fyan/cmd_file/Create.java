/*
 * MIT License
 *
 * Copyright (c) 2019 xubin zhao
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fyan.cmd_file;

import fyan.FyanApplication;
import fyan.base.CommandBase;
import fyan.units.BinaryConver;

import java.io.File;
import java.io.IOException;

//      -c | --create [total] <constName> [进值+步长+位数] <a/d>
//      -c | --create -l [dictionaryName...]
public class Create implements CommandBase {
    public int resInfo(String[] args) throws IOException {

        return args[1].equals("-l") ? createList(args) : create(args);
    }

    private int createList(String[] args) {
        for (int i = 2; i < args.length; i++) {
            File file = new File(FyanApplication.LOCAL_PATH + args[i]);
            file.mkdir();
        }
        return 0;
    }

    private int create(String[] args) {

        String sort = args[args.length - 1];
        int len = sort.matches("[a|d]") ? args.length - 1 : args.length;
        sort = sort.matches("[a|d]") ? sort : "a";

        int fileTotal = Integer.valueOf(args[1]);

        String constName = "";
        if (len == 4)
            constName = args[2];
        //进制+步长+位数
        int[] namingRules = new int[3];
        int index = 0;
        for (String str : args[3].trim().split("\\+"))
            namingRules[index++] = Integer.valueOf(str);

        for (int i = 0; i < fileTotal; i++) {
            String name = sort == "d" ? BinaryConver.transform(i * namingRules[1], namingRules[0], namingRules[2]) + constName
                    : constName + BinaryConver.transform(i * namingRules[1], namingRules[0], namingRules[2]);
            File file = new File(FyanApplication.LOCAL_PATH + name);
            file.mkdir();
        }


        return 0;
    }
}