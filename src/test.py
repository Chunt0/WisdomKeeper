from whispercpp import Whisper

w = Whisper('large')

result = w.transcribe("./WAV/test1.mp3")
text = w.extract_text(result)
print(text)