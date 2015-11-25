package org.scherzoteller.csndGen.spectrogram

import com.musicg.wave.Wave
import com.musicg.wave.extension.Spectrogram
import com.musicg.graphic.GraphicRender
import org.apache.commons.io.FilenameUtils
import java.io.File
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SpectrogramUtils {
  
}
object SpectrogramUtils {
  val  LOG: Logger = LoggerFactory.getLogger(classOf[SpectrogramUtils]);
  // see also http://spek.cc/... more beautiful!
  def renderSpectrogramFile(waveIn: String, imgOut: String, fftSampleSize: Int, overlapFactor: Int) = {
    val wave = new Wave(waveIn)
    val spectrogram = new Spectrogram(wave, fftSampleSize, overlapFactor)
    val render = new GraphicRender()
    // FIXME bug in musicg: this should be enough... but fails with NoSuchMethodError
    //render.renderSpectrogram(spectrogram, imgOut)
    
    val spData =  spectrogram.getAbsoluteSpectrogramData()
    if(spData.length > 0)
      render.renderSpectrogramData(spData, imgOut);
    else
      LOG.warn("empty wav, nothing to render")
  }
  def renderSpectrogramFile(waveIn: String, imgOut: String): Unit = {
    renderSpectrogramFile(waveIn, imgOut, 1024, 2)
  }
  def renderSpectrogramFile(waveIn: String): Unit = {
    val imgOut = FilenameUtils.removeExtension(new File(waveIn).getCanonicalPath()) + ".jpg"
    renderSpectrogramFile(waveIn, imgOut)
  }
}

