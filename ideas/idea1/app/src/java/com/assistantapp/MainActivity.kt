import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
private val RECORD_AUDIO_PERMISSION_CODE = 101

private var mediaRecorder: MediaRecorder? = null
private var audioFilePath: String? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startRecordingButton: Button = findViewById(R.id.startRecordingButton)
        val stopRecordingButton: Button = findViewById(R.id.stopRecordingButton)

        startRecordingButton.setOnClickListener {
        if (checkPermission()) {
        startRecording()
        } else {
        requestPermission()
        }
        }

        stopRecordingButton.setOnClickListener {
        stopRecording()
        // Send audio file to the server for processing
        // Implement server communication logic here
        }

        audioFilePath = externalCacheDir!!.absolutePath + "/audio_record.3gp"
        }

private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED
        }

private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION_CODE)
        }

private fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder!!.setOutputFile(audioFilePath)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
        mediaRecorder!!.prepare()
        mediaRecorder!!.start()
        } catch (e: Exception) {
        e.printStackTrace()
        }
        }

private fun stopRecording() {
        if (mediaRecorder != null) {
        mediaRecorder!!.stop()
        mediaRecorder!!.release()
        mediaRecorder = null
        }
        }
        }
