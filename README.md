# Godot Android Text to Speech

This is a Android plugin for [Godot Engine](https://github.com/godotengine/godot) 3.4 or higher.

This plugin supports:
- Text to speech in different locale

## Setup

- Configure, install  and enable the "Android Custom Template" for your project, just follow the [official documentation](https://docs.godotengine.org/en/latest/getting_started/workflow/export/android_custom_build.html);
- go to the ```release tab```, choose a version and download the respective package;
- extract the package and put```GodotTTS.gdap``` and ```GodotTTS.x.y.z.release.aar``` inside the ```res://android/plugins``` directory on your Godot project.
- on the Project -> Export... -> Android -> Options -> 
    - Custom Template: check the _Use Custom Build_
    - Plugins: check the _Godot # Godot Tts_ (this plugin)

## API Reference

### Methods
```python

# Init TTS engine with language and country (example -> "en", "US")
initTextToSpeech(String lang, String country)

# Is languange can be used in engine, returns constant
isLanguageAvailable(String lang, String country)

# TTS, play text with specified voice
textToSpeech(String text)

# Stop playing voice
stop()

```

## Compiling the Plugin (optional)

If you want to compile the plugin by yourself, it's very easy:
1. clone this repository;
2. checkout the desired version;
3. open ```godot-text-to-speech``` directory in ```Android Studio```
4. don't forget to put ```godot-lib.release.aar``` to ```godot-lib.release``` directory

If everything goes fine, you'll find the ```.aar``` files at ```godot-text-to-speech/godot-tts/build/outputs/aar/```.

## Troubleshooting

* First of all, please make sure you're able to compile the custom build for Android without plugin, this way we can isolate the cause of the issue.

* Using logcat for Android is the best way to troubleshoot most issues. You can filter Godot only messages with logcat using the command: 
```
adb logcat -s godot
```

## References

Google Developers:
* https://developer.android.com/reference/android/speech/tts/TextToSpeech

## License

MIT license
