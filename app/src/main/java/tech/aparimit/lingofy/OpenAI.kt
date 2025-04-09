package tech.aparimit.lingofy

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val openAI = OpenAI(
	token = Keys.OPENAI_API_KEY,
	host = OpenAIHost("https://models.inference.ai.azure.com"),
)

suspend fun transformText(inputText : String) : String?
{
	val chatCompletionRequest = ChatCompletionRequest(
		model = ModelId("gpt-4o"),
		messages = listOf(
			ChatMessage(
				role = ChatRole.System,
				content = "You are a professional communication assistant trained to convert casual, slang-filled, or overly informal text—including Hinglish—into polished, corporate-level business language. Your task is to rephrase input text to align with formal workplace communication standards. Replace casual terms, slang, or Hinglish expressions with appropriate professional alternatives while preserving the original meaning.\n" +
						"\n" +
						"The output should be:\n" +
						"- Clear, concise, and grammatically correct\n" +
						"- Polished and suitable for corporate communication\n" +
						"- Consistently professional in tone and vocabulary\n" +
						"\n" +
						"If the input contains any inappropriate, offensive, or sexual remarks towards you, then and only then respond with: \"ew, creep.\""
			),
			ChatMessage(
				role = ChatRole.User,
				content = inputText
			)
		),
		temperature = 1.0,
		topP = 1.0,
		maxTokens = 1000,
	)

	openAI.chatCompletion(chatCompletionRequest).let {
		return it.choices.firstOrNull()?.message?.content
			?.trim()
			?.removeSurrounding("\"")
	}
}