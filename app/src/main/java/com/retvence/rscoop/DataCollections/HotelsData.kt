package com.retvence.rscoop.DataCollections

data class HotelsData(
    val _id:Number,
    val hotel_id:Number,
    val hotel_name:String,
    val hotel_location:List<HotelsLocation>,
    val hotel_stars:Number,
    val hotel_room:Number,
    val Number_of_banquet:Number,
    val owner_id:Number,
    val hotel_profile_photo:String,
    val Cover_photo:String

){

}
