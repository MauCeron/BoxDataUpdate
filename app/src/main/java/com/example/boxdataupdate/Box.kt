package com.example.boxdataupdate

import org.json.JSONObject
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class Box(var serialNumber: String) {

    private lateinit var config: JSONObject
    var intervalMinutes: Int = 0
    //var serialNumber = "ZTEATV011234567890"
    var a = 0
    var b = 0
    lateinit var hourInitial: String
    lateinit var hourFinal: String
    var minutesBySerial = 0
    lateinit var hourToUpdate: String


    fun initBox() {
        config = JSONObject(jsonConfig)
        intervalMinutes = config.getInt("minutes_per_interval")
        getInterval()
    }

    /**
     * Get last 2 numbers and penultimate 2 numbers from serial number
     */
    private fun getLastNumbers() {
        val lastNumbers = serialNumber.substring(serialNumber.length - 4).toCharArray()

        val penultimate2 = lastNumbers[0] + "" + lastNumbers[1]
        val last2 = lastNumbers[2] + "" + lastNumbers[3]

        a = last2.toInt()
        b = penultimate2.toInt()
    }


    /**
     * Get interval in which the box can launch the request
     */
    private fun getInterval() {
        val intervals = config.getJSONArray("intervals")

        getLastNumbers()

        for (i in 0 until intervals.length()) {
            val item = intervals.getJSONObject(i)
            val serialInitial = item.getInt("serial_initial")
            val serialFinal = item.getInt("serial_final")

            if (a in serialInitial..serialFinal) {
                hourInitial = item.getString("hour_initial")
                hourFinal = item.getString("hour_final")
                break
            }
        }

        getHourOfUpdate()

    }

    /**
     * Get the request launch time depending on the 2 penultimate serial numbers
     */
    private fun getHourOfUpdate(){
        minutesBySerial = ((intervalMinutes * b) / 100)

        val time = LocalTime.parse(hourInitial)
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        hourToUpdate = formatter.format(time.plusMinutes(minutesBySerial.toLong()))

    }

}