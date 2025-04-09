package tech.aparimit.lingofy

import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

	@Preview(showBackground = true)
	@Composable
	fun MainScreen() {

		val context = LocalContext.current
		val clipboardManager = LocalClipboardManager.current
		
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
				.verticalScroll(rememberScrollState())
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
					val inputMethodManager : InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
					inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

					Log.d("MainActivity->", "Button Clicked")
					lifecycleScope.launch(Dispatchers.IO) {
						outputText = transformText(inputText) ?: "Unable to process request. Try again!"
					}.invokeOnCompletion {
						Log.d("MainActivity->", "invokeOnCompletion with $outputText")
					}
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

			Button(
				modifier = Modifier
					.padding(
						start = 0.dp,
						end = 0.dp,
						top = 24.dp,
						bottom = 0.dp
					)
					.clip(RoundedCornerShape(32.dp)),
				onClick = {
					clipboardManager.setText(AnnotatedString(outputText))
					Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
				},
				colors = ButtonDefaults.buttonColors(
					containerColor = textFieldColor,
					disabledContainerColor = textFieldColor,
					contentColor = Color.Black,
					disabledContentColor = Color.Black,
				),
				contentPadding = PaddingValues(
					horizontal = 64.dp,
					vertical = 12.dp
				),
				shape = RectangleShape
			) {
				Icon(
					modifier = Modifier
						.size(30.dp),
					painter = painterResource(R.drawable.ic_copy),
					tint = Color.Black,
					contentDescription = "Copy to Clipboard",
				)
			}
		}
	}
}