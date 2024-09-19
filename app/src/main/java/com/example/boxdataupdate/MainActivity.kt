package com.example.boxdataupdate

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.boxdataupdate.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listOfBoxes = mutableListOf<Box>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createBox()
        binding.createRandom.setOnClickListener { createBox() }
        binding.randomNum.setOnClickListener { getRandomSerial() }
        binding.createCustom.setOnClickListener { createCustomBox() }

    }


    private fun createBox() {
        createBox(generateSerial())
        /*val box = Box(generateSerial())
        box.initBox()
        addToList(box)*/
    }

    private fun createBox(customSerial: String) {
        val box = Box(customSerial)
        box.initBox()
        addToList(box)
    }

    private fun addToList(box: Box){
        listOfBoxes.add(0,box)
        val boxes = mutableListOf<String>()

        for (i in listOfBoxes) {
            var data = "Serial: ZTEATV${i.serialNumber}\nlast two: ${i.a} penultimate two: ${i.b}\n" +
                    "Interval: ${i.hourInitial}hrs - ${i.hourFinal}hrs\nmin to at: ${i.minutesBySerial}\n" +
                    "Hour to make the daily request to epg/channel: ${i.hourToUpdate}hrs"

            boxes.add(data)
        }

        binding.listview.adapter = ArrayAdapter(this@MainActivity, R.layout.simple_list_item_1, boxes)
    }

    /**
     * Get serial random
     */
    private fun generateSerial(): String = (10000000000L until 100000000000L).random().toString()

    private fun getRandomSerial() {
        binding.customNumber.setText(generateSerial())
    }

    private fun createCustomBox() {
        val customSerial = binding.customNumber.text
        if ( customSerial.length >= 4) {
            createBox(customSerial.toString())
        }
    }
}