package fr.mazure.aitestcasegeneration.provider.custom;

import dev.langchain4j.model.chat.ChatModel;
import fr.mazure.aitestcasegeneration.provider.base.MissingEnvironmentVariable;
import fr.mazure.aitestcasegeneration.provider.base.ModelProvider;
import fr.mazure.aitestcasegeneration.provider.custom.internal.CustomChatModel;
import fr.mazure.aitestcasegeneration.provider.custom.internal.CustomChatModelBuilder;

public class CustomChatModelProvider implements ModelProvider {

    public static ChatModel createChatModel(final CustomModelParameters parameters) throws MissingEnvironmentVariable {

        final String apiKey = ModelProvider.getApiKeyFromEnvironmentVariable(parameters.getApiKeyEnvironmentVariableName(), "custom");

        final CustomChatModelBuilder builder = CustomChatModel.builder()
                                                              .apiKey(apiKey)
                                                              .baseUrl(parameters.getBaseUrl().get().toString())
                                                              .payloadTemplate(parameters.getPayloadTemplate())
                                                              .answerPath(parameters.getAnswerPath())
                                                              .httpHeaders(parameters.getHttpHeaders())
                                                              .inputTokenPath(parameters.getInputTokenPath())
                                                              .outputTokenPath(parameters.getOutputTokenPath());

        if (parameters.getLogRequests().isPresent()) {
            builder.logRequests(parameters.getLogRequests().get());
        }

        if (parameters.getLogResponses().isPresent()) {
            builder.logResponses(parameters.getLogResponses().get());
        }

        return builder.build();
    }
}
