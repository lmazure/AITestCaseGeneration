package fr.mazure.aitestcasegeneration.provider.custom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import org.yaml.snakeyaml.Yaml;

import fr.mazure.aitestcasegeneration.provider.base.InvalidModelParameter;
import fr.mazure.aitestcasegeneration.provider.base.MissingModelParameter;
import fr.mazure.aitestcasegeneration.provider.base.ModelParameters;
import fr.mazure.aitestcasegeneration.provider.base.ParameterMap;

/**
 * Parameters for the custom model provider.
 *
 * @param modelName           the name of the model
 * @param url                 the URL of the provider
 * @param apiKeyEnvVar        the name of the environment variable containing the API key
 * @param payloadTemplate     the payload template for the API calls
 * @param httpHeaders         the HTTP headers to be sent with the API requests
 * @param answerPath          the path to the answer in the API response
 * @param inputTokenPath      the path to the input token count in the API response
 * @param outputTokenPath     the path to the output token count in the API response
 * @param logRequests         whether to log the requests
 * @param logResponses        whether to log the responses
 */
public class CustomModelParameters extends ModelParameters {

    private final String payloadTemplate;
    private final Map<String, String> httpHeaders;
    private final String answerPath;
    private final String inputTokenPath;
    private final String outputTokenPath;
    private final Optional<Boolean> logRequests;
    private final Optional<Boolean> logResponses;

    public CustomModelParameters(final String modelName,
                                 final URL url,
                                 final String apiKeyEnvVar,
                                 final String payloadTemplate,
                                 final Map<String, String> httpHeaders,
                                 final String answerPath,
                                 final String inputTokenPath,
                                 final String outputTokenPath,
                                 final Optional<Boolean> logRequests,
                                 final Optional<Boolean> logResponses) {
        super(modelName, Optional.of(url), apiKeyEnvVar);
        this.payloadTemplate = payloadTemplate;
        this.httpHeaders = httpHeaders;
        this.answerPath = answerPath;
        this.inputTokenPath = inputTokenPath;
        this.outputTokenPath = outputTokenPath;
        this.logRequests = logRequests;
        this.logResponses = logResponses;
    }

    public String getPayloadTemplate() {
        return payloadTemplate;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public String getAnswerPath() {
        return answerPath;
    }

    public String getInputTokenPath() {
        return inputTokenPath;
    }

    public String getOutputTokenPath() {
        return outputTokenPath;
    }

    public Optional<Boolean> getLogRequests() {
        return logRequests;
    }

    public Optional<Boolean> getLogResponses() {
        return logResponses;
    }

    /**
     * Load parameters from a YAML file and create a new CustomModelParameters instance.
     *
     * @param yamlFilePath the path to the YAML file containing the parameters
     * @return a new CustomModelParameters instance with the parameters from the YAML file
     * @throws IOException if there is an error reading the file
     * @throws MissingModelParameter if a compulsory parameter is missing
     * @throws InvalidModelParameter if a parameter has an incorrect value
     */
    public static CustomModelParameters loadFromFile(final Path yamlFilePath) throws IOException, MissingModelParameter, InvalidModelParameter {
        try (final InputStream inputStream = new FileInputStream(yamlFilePath.toFile())) {
            final Yaml yaml = new Yaml();
            final ParameterMap parameterMap = new ParameterMap(yaml.load(inputStream));
            return new CustomModelParameters(parameterMap.getString("modelName"),
                                             parameterMap.getUrl("url"),
                                             parameterMap.getString("apiKeyEnvVar"),
                                             parameterMap.getString("payloadTemplate"),
                                             parameterMap.getMap("httpHeaders"),
                                             parameterMap.getString("answerPath"),
                                             parameterMap.getString("inputTokenPath"),
                                             parameterMap.getString("outputTokenPath"),
                                             parameterMap.getOptionalBoolean("logRequests"),
                                             parameterMap.getOptionalBoolean("logResponses"));
        }
    }
}
