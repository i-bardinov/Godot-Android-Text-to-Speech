package ibardinov.godot.plugin.android.tts;

import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GodotTTS extends GodotPlugin {
    private TextToSpeech textToSpeech = null;

    public GodotTTS(Godot godot) {
        super(godot);
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodotTTS";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList(
                "initTextToSpeech", "isLanguageAvailable", "textToSpeech", "stop"
        );
    }

    /**
     * Init TTS
     *
     */
    public void initTextToSpeech(String lang, String country) {
        if (textToSpeech != null)
            return;
        Locale locale = new Locale(lang, country);
        textToSpeech = new TextToSpeech(getActivity(), status -> {
            if(status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(locale);
            }
        });
    }

    /**
     * Is language available, returns Constants
     *
     */
    public int isLanguageAvailable(String lang, String country) {
        if (textToSpeech == null)
            return TextToSpeech.ERROR;
        Locale locale = new Locale(lang, country);
        return textToSpeech.isLanguageAvailable(locale);
    }

    /**
     * Translate text to speech
     *
     */
    public void textToSpeech(String text) {
        if (textToSpeech == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     * Stop text to speech
     *
     */
    public void stop() {
        if (textToSpeech != null)
            textToSpeech.stop();
    }

    @Override
    public void onMainPause() {
        stop();
    }

    @Override
    public void onMainDestroy() {
        textToSpeech.shutdown();
    }
}
