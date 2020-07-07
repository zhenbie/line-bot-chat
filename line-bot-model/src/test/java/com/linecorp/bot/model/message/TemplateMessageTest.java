/*
 * Copyright 2020 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package com.linecorp.bot.model.message;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import com.linecorp.bot.model.message.sender.Sender;
import com.linecorp.bot.model.message.template.ButtonsTemplate;

import java.net.MalformedURLException;
import java.net.URL;

public class TemplateMessageTest {

    @Test
    public void constructor() {
        TemplateMessage message = new TemplateMessage("hello", ButtonsTemplate.builder().build());
        assertThat(message.getAltText()).isEqualTo("hello");
        assertThat(message.getTemplate()).isNotNull();
        assertThat(message.getQuickReply()).isNull();
        assertThat(message.getSender()).isNull();
    }

    @Test
    public void builder() throws JsonProcessingException, MalformedURLException {
        TemplateMessage message = TemplateMessage.builder()
                .altText("hello")
                .template(ButtonsTemplate.builder().imageAspectRatio("image://lisd.com").build())
                .sender(Sender.builder().name("http://dddx.com").build())
                .build();
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(message);
        System.out.println(s);
        assertThat(message.getAltText()).isEqualTo("hello");
        assertThat(message.getTemplate()).isNotNull();
        assertThat(message.getQuickReply()).isNull();
        assertThat(message.getSender()).isNotNull();
    }
}
