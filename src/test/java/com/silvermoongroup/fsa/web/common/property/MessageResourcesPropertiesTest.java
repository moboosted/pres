package com.silvermoongroup.fsa.web.common.property;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


@Tag("com.silvermoongroup.base.junit.categories.UnitTests")
public class MessageResourcesPropertiesTest {

    private static Logger LOG = LoggerFactory.getLogger(MessageResourcesPropertiesTest.class);

    private static final String DEFAULT_LOCALE = "en";

    private static final String[] LOCALES_TO_COMPARE = {"fr"};

    private static LinkedHashMap<String, String> defaultMessageResources = new LinkedHashMap<>();
    private static Map<String, LinkedHashMap<String, String>> messageResources = new LinkedHashMap<>();

    @BeforeAll
    public static void setup() throws IOException {

        File resourcesDirectory = new File(System.getProperty("user.dir") + "/src/main/resources/com/silvermoongroup/fsa/web/common/");

        if (resourcesDirectory.exists()) {
            for (final File messageResourcesFile : resourcesDirectory.listFiles()) {
                String locale = resolveLocale(messageResourcesFile.getName());
                if (locale.contains(DEFAULT_LOCALE) || Arrays.stream(LOCALES_TO_COMPARE).anyMatch(locale::contains)) {
                    processFile(messageResourcesFile, locale);
                }
            }
        }

    }

    @Test
    public void testMissingProperties() {

        if (messageResources != null) {
            messageResources.keySet().stream().forEach(locale -> {

                LOG.info("#################################Comparing default and {}################################################", locale);
                LinkedHashMap<String, String> localePropertiesFile = messageResources.get(locale);

                Collection<String> missingInDefault = new ArrayList<>(localePropertiesFile.keySet());
                missingInDefault.removeAll(defaultMessageResources.keySet());

                Collection<String> presentInDefault = new ArrayList<>(defaultMessageResources.keySet());
                presentInDefault.removeAll(localePropertiesFile.keySet());

                if (presentInDefault.isEmpty() == false) {

                    LOG.info("START Missing Values in Locale : {}", locale);

                    presentInDefault.stream().forEach(missingProperty -> {
                        System.out.println(missingProperty + "=[" + locale + "]" + defaultMessageResources.get(missingProperty));
                    });
                    LOG.info("END Missing Values in Locale : {}", locale);

                }

                if (missingInDefault.isEmpty() == false) {

                    LOG.info("START Missing Values in Default");

                    missingInDefault.stream().forEach(missingProperty -> {
                        System.out.println(missingProperty + "=[en]" + localePropertiesFile.get(missingProperty));
                    });

                    LOG.info("END Missing Values in Default");
                }


                LOG.info("###################END Comparing default and {}###########################", locale);

                Assertions.assertEquals(presentInDefault.size(), 0);
                Assertions.assertEquals(missingInDefault.size(), 0);

            });
        }

    }

    @Test
    public void testUnchangedProperties() {

        messageResources.keySet().stream().forEach(locale -> {

            LOG.info("#################################Comparing english and {}################################################", locale);

            LinkedHashMap<String, String> localePropertiesFile = messageResources.get(locale);
            System.out.println("No translations for following message resources");
            defaultMessageResources.keySet().parallelStream().forEach(keyInEnglish -> {
                if (defaultMessageResources.get(keyInEnglish).equalsIgnoreCase(localePropertiesFile.get(keyInEnglish))) {
                    System.out.println(keyInEnglish+"="+defaultMessageResources.get(keyInEnglish));
                }
            });

            LOG.info("#################################END Comparing english and {}################################################", locale);

        });

    }


    private static void processFile(File messageResourcesFile, String locale) throws IOException {

        final int lhs = 0;
        final int rhs = 1;

        LinkedHashMap<String, String> propertiesMap = new LinkedHashMap<>();

        BufferedReader bfr = new BufferedReader(new FileReader(messageResourcesFile));

        String line;

        while ((line = bfr.readLine()) != null) {
            if (!line.startsWith("#") && !line.isEmpty()) {
                String[] pair = line.trim().split("=");
                if (pair.length > 1) {
                    propertiesMap.put(pair[lhs].trim(), pair[rhs].trim());
                }
            }
        }
        bfr.close();

        if (DEFAULT_LOCALE.equalsIgnoreCase(locale)) {
            defaultMessageResources = propertiesMap;
        } else {
            messageResources.put(locale, propertiesMap);
        }

    }

    private static String resolveLocale(String name) {
        name = FilenameUtils.removeExtension(name);
        return name.substring(name.indexOf("_") + 1);
    }

}
