/**
 * Copyright (C) 2018 Idan Rozenfeld the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rozidan.springboot.levelog;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LevelogKafka {

    public final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final LevelogProvider levelogProvider;

    @Autowired
    public LevelogKafka(LevelogProvider levelogProvider) {
        this.levelogProvider = levelogProvider;
    }

    @KafkaListener(topics = "${levelog.kafka.topic:levelog.t}",
            containerFactory = "${levelog.kafka.container:levelogKafkaContainer}")
    public void changeLogLevel(Message message) {
        levelogProvider.changeLogLevel(message);
        countDownLatch.countDown();
    }

}