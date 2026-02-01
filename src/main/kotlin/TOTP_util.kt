package ru

import java.nio.ByteBuffer
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.pow

const val totp_sample_secret_key = "AUSJD7LZ5H27TAC7NW2IJMATDMVDUPUG"

object TotpUtil {
    private const val TIME_STEP_SECONDS = 30
    private const val DIGITS = 6
    private const val HMAC_ALGO = "HmacSHA1"

    fun generateTotp(secretBase32: String, timeMillis: Long = System.currentTimeMillis()): String {
        val key = base32Decode(secretBase32)
        val counter = (timeMillis / 1000) / TIME_STEP_SECONDS

        val buffer = ByteBuffer.allocate(8)
        buffer.putLong(counter)
        val counterBytes = buffer.array()

        val mac = Mac.getInstance(HMAC_ALGO)
        mac.init(SecretKeySpec(key, HMAC_ALGO))
        val hash = mac.doFinal(counterBytes)

        val offset = hash.last().toInt() and 0x0F
        val binary =
            ((hash[offset].toInt() and 0x7F) shl 24) or
                    ((hash[offset + 1].toInt() and 0xFF) shl 16) or
                    ((hash[offset + 2].toInt() and 0xFF) shl 8) or
                    (hash[offset + 3].toInt() and 0xFF)

        val otp = binary % 10.0.pow(DIGITS).toInt()
        return otp.toString().padStart(DIGITS, '0')
    }

    private fun base32Decode(input: String): ByteArray {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"
        val cleaned = input.replace("=", "").uppercase()

        val bytes = ArrayList<Byte>()
        var buffer = 0
        var bitsLeft = 0

        for (char in cleaned) {
            val value = alphabet.indexOf(char)
            if (value < 0) continue

            buffer = (buffer shl 5) or value
            bitsLeft += 5

            if (bitsLeft >= 8) {
                bytes.add(((buffer shr (bitsLeft - 8)) and 0xFF).toByte())
                bitsLeft -= 8
            }
        }
        return bytes.toByteArray()
    }
}