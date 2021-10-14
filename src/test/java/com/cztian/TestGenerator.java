package com.cztian;

import org.junit.Test;

public class TestGenerator {

    @Test
    public void testGenerate() {
        GeneratorConfig gc = new GeneratorConfig();
        gc.setOutput(System.getProperty("user.dir") + "/src/main/java");
        gc.setPackageName("com.cztian.model");
        gc.useLombok(false);
        gc.setChainAccess(true);
        gc.setAllArgsConstructor(true);
        Generator generator = new Generator(gc);
        generator.generate();
    }
}
