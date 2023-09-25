package com.revature.utils;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfanityFilter {
    public boolean hasProfanity(String text) throws IOException {
        String[] textArr = text.replaceAll("\\n", " ").split(" ");

        Set<String> whitelist = new HashSet<>(Arrays.asList(
                "peniston", "arsenic", "buttress", "scunthorpe"
        ));
        Set<String> blacklist = new HashSet<>(Arrays.asList(
                "fuck", "cunt", "arse", "penis", "bitch", "shit", "cock", "tits", "motherfucker"
        ));
        Object[] blacklistArr = blacklist.toArray();
        for (String word : textArr) {
            if (whitelist.contains(word)) {
                continue;
            }
            for (Object profanity : blacklistArr) {
                if (word.contains((String) profanity)) {
                    return true;
                }
            }
        }
        return false;
    }
}
