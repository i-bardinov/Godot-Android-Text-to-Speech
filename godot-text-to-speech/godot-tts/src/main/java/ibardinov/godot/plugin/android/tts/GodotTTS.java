package ibardinov.godot.plugin.android.tts;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
                "setLanguage", "isLanguageAvailable", "speak", "isSpeaking", "setPitch", "setSpeechRate", "stop"
        );
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("start"));
        signals.add(new SignalInfo("done"));

        return signals;
    }

    /**
     * Init TTS
     *
     */
    public void setLanguage(String lang, String country) {
        if (textToSpeech != null)
            return;
        Locale locale = new Locale(lang, country);
        textToSpeech = new TextToSpeech(getActivity(), status -> {
            if(status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(locale);
                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        emitSignal("start");
                    }
                    
                    @Override
                    public void onDone(String utteranceId) {
                        emitSignal("done");
                    }

                    @Override
                    public void onError(String utteranceId) {
                    }
                });
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
    public void speak(String text) {
        if (textToSpeech == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     * Checks whether the TTS engine is busy speaking
     *
     */
    public boolean isSpeaking() {
        if(textToSpeech == null)
            return false;
        
        return textToSpeech.isSpeaking();
    }

    /**
     * Sets the speech pitch for the TextToSpeech engine
     *
     */
    public int setPitch(float pitch) {
        if(textToSpeech == null)
            return textToSpeech.ERROR;
        
        return textToSpeech.setPitch(pitch);
    }

    /**
     * Sets the speech rate
     *
     */
    public int setSpeechRate(float speechRate) {
        if(textToSpeech == null)
            return textToSpeech.ERROR;
        
        return textToSpeech.setSpeechRate(speechRate);
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
        if (textToSpeech != null)
            textToSpeech.shutdown();
    }
}
