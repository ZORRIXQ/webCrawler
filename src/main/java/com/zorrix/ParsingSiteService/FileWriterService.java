package com.zorrix.ParsingSiteService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class FileWriterService {
    public void writeToFile(Set<String> urls){
        try (FileWriter writer = new FileWriter("Urls.txt", true)) {
            for (String e : urls) {
                writer.write(e + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void writeToFile(String str){
        try (FileWriter writer = new FileWriter("data.txt", true)) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
