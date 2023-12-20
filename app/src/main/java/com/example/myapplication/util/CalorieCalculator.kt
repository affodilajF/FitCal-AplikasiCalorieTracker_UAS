package com.example.myapplication.util

object CalorieCalculator {

    fun getRemainingCal(targetedCal : Int, achievedCal: Int): String {
        val c = (targetedCal - achievedCal)
        return c.toString()
    }
    fun getCalCarbs(gram: String?): Int {
        return gram?.toIntOrNull()?.times(4) ?: 0
    }
    fun getCalProtein(gram: String?): Int {
        return gram?.toIntOrNull()?.times(4) ?: 0
    }

    fun getCalFat(gram: String?): Int {
        return gram?.toIntOrNull()?.times(9) ?: 0
    }

    //    ------------- Add Custom Menu
    fun getCalCarbsOnUserServing(gram: String, servings: String): String {
        val a = getCalCarbs(gram)
        val b = servings.toDoubleOrNull() ?: 0.0
        return String.format("%.1f", a * b)
    }

    fun getCalProteinOnUserServing(gram: String, servings: String): String {
        val a = getCalProtein(gram)
        val b = servings.toDoubleOrNull() ?: 0.0
        return String.format("%.1f", a * b)
    }

    fun getCalFatOnUserServing(gram: String, servings: String): String {
        val a = getCalFat(gram)
        val b = servings.toDoubleOrNull() ?: 0.0
        return String.format("%.1f", a * b)
    }

    fun getTotalCal(servings: String?, calories: String?): String {
        val a = servings?.toDoubleOrNull() ?: 0.0
        val b = calories?.toDoubleOrNull() ?: 0.0

        return String.format("%.0f", a * b)
    }

    fun getCalculatedAllCalories(gram: String?, gram2: String, gram3: String): String {
        val a = getCalCarbs(gram).toDouble()
        val b = getCalProtein(gram2).toDouble()
        val c = getCalFat(gram3).toDouble()

        val totalCalories = a + b + c
        return totalCalories.toString()
    }

//    -------- update
    fun getCalCarbs(gram : Int) : String {
        return (gram*4).toString()
    }

    fun getCalProtein(gram: Int): String {
        return (gram*4).toString()
    }

    fun getCalFat(gram: Int): String {
        return (gram*9).toString()
    }

    fun getTotalCal100(carbs: Int, protein: Int, fat: Int): Int {
        return (carbs * 4) + (protein * 4) + (fat * 9)
    }

    fun getTotalCal100(carbs: String, protein: String, fat: String): String {
        val carbsInt = carbs.toIntOrNull() ?: 0
        val proteinInt = protein.toIntOrNull() ?: 0
        val fatInt = fat.toIntOrNull() ?: 0

        val totalCalories = (carbsInt * 4) + (proteinInt * 4) + (fatInt * 9)
        return totalCalories.toString()
    }

    // home ----
    fun getPercentProgress(dayTargetedCal : Int, achievedCal: Int) : Int {
    //        caloric target perhari
        val a = dayTargetedCal.toDouble()
    //        caloric sekarang
        val b = achievedCal.toDouble()
    //        percent progress
        val c = (b/a)*100

        return c.toInt()
    }

    fun getPercentProgressEachComponentGram (dayTargetedGram : Int, achievedGram: Double) : Int {
//        caloric target perhari
        val a = dayTargetedGram.toDouble()
//        caloric sekarang
        val b = achievedGram.toDouble()
//        percent progress
        val c = (b/a)*100

        return c.toInt()
    }


}