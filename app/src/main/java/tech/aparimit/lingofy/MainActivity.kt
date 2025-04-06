package tech.aparimit.lingofy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.aparimit.lingofy.ui.theme.backgroundColor
import tech.aparimit.lingofy.ui.theme.hintTextColor
import tech.aparimit.lingofy.ui.theme.montserratFontFamily
import tech.aparimit.lingofy.ui.theme.textFieldColor

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MainScreen()
		}
	}

	private fun convertText(){
	}

	@Preview(showBackground = true)
	@Composable
	fun MainScreen() {

		var btnEnabled by remember {
			mutableStateOf(false)
		}

		var inputText by remember {
			mutableStateOf("")
		}

		var outputText by remember {
			mutableStateOf("")
		}

		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(backgroundColor),
			horizontalAlignment = Alignment.CenterHorizontally
		){
			Text(
				text = stringResource(R.string.app_name),
				modifier = Modifier
					.fillMaxWidth()
					.padding(
						start = 0.dp,
						end = 0.dp,
						top = 24.dp,
						bottom = 0.dp
					),
				textAlign = TextAlign.Center,
				fontFamily = montserratFontFamily,
				fontSize = 24.sp,
				fontWeight = FontWeight.Bold,
			)

			TextField(
				value = inputText,
				onValueChange = {
					inputText = it
					if (inputText.isNotEmpty()) {
						btnEnabled = true
					} else {
						btnEnabled = false
					}
				},
				modifier = Modifier
					.padding(
						start = 48.dp,
						end = 48.dp,
						top = 24.dp,
						bottom = 0.dp
					)
					.clip(RoundedCornerShape(16.dp))
					.fillMaxWidth()
					.height(250.dp),
				enabled = true,
				readOnly = false,
				textStyle = TextStyle(
					color = Color.Black,
					fontSize = 18.sp,
					fontFamily = montserratFontFamily,
					fontWeight = FontWeight.Normal,
					letterSpacing = 0.5.sp,
				),
				placeholder = {
					Text(
						text = "Put in your casual text here",
						fontSize = 18.sp,
						fontFamily = montserratFontFamily,
						fontWeight = FontWeight.Normal,
						color = hintTextColor
					)
				},
				colors = TextFieldDefaults.colors(
					focusedTextColor = Color.Black,
					unfocusedTextColor = Color.Black,
					disabledTextColor = Color.Black,
					errorTextColor = Color.Black,
					focusedContainerColor = textFieldColor,
					unfocusedContainerColor = textFieldColor,
					disabledContainerColor = textFieldColor,
					errorContainerColor = textFieldColor,
					cursorColor = Color.Black,
					errorCursorColor = Color.Black,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent,
					disabledIndicatorColor = Color.Transparent,
					errorIndicatorColor = Color.Transparent,
				)
			)

			Button(
				modifier = Modifier
					.padding(
						start = 0.dp,
						end = 0.dp,
						top = 24.dp,
						bottom = 0.dp
					),
				onClick = {
					convertText()
				},
				enabled = btnEnabled,
				colors = ButtonDefaults.buttonColors(
					containerColor = Color.Black,
					disabledContainerColor = hintTextColor,
					contentColor = textFieldColor,
					disabledContentColor = textFieldColor,
				),
				contentPadding = PaddingValues(
					horizontal = 20.dp,
					vertical = 20.dp
				),
				shape = CircleShape
			) {
				Icon(
					imageVector = ImageVector.vectorResource(R.drawable.ic_convert),
					tint = textFieldColor,
					contentDescription = "Convert Text",
				)
			}

			TextField(
				value = outputText,
				onValueChange = {
					outputText = it
				},
				modifier = Modifier
					.padding(
						start = 48.dp,
						end = 48.dp,
						top = 24.dp,
						bottom = 0.dp
					)
					.clip(RoundedCornerShape(16.dp))
					.fillMaxWidth()
					.height(250.dp),
				enabled = true,
				readOnly = true,
				textStyle = TextStyle(
					color = Color.Black,
					fontSize = 18.sp,
					fontFamily = montserratFontFamily,
					fontWeight = FontWeight.Normal,
					letterSpacing = 0.5.sp,
				),
				placeholder = {
					Text(
						text = "And I will ling-o-fy it for you",
						fontSize = 18.sp,
						fontFamily = montserratFontFamily,
						fontWeight = FontWeight.Normal,
						color = hintTextColor
					)
				},
				colors = TextFieldDefaults.colors(
					focusedTextColor = Color.Black,
					unfocusedTextColor = Color.Black,
					disabledTextColor = Color.Black,
					errorTextColor = Color.Black,
					focusedContainerColor = textFieldColor,
					unfocusedContainerColor = textFieldColor,
					disabledContainerColor = textFieldColor,
					errorContainerColor = textFieldColor,
					cursorColor = Color.Black,
					errorCursorColor = Color.Black,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent,
					disabledIndicatorColor = Color.Transparent,
					errorIndicatorColor = Color.Transparent,
				)
			)
		}
	}
}