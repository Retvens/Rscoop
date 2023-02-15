package com.retvence.rscoop.DataCollections

data class HotelsData(
    val _id:String,
    val hotel_id:String,
    val hotel_name:String,
    val hotel_location: List<HotelsLocation>,
    val hotel_stars:String,
    val hotel_room:String,
    val Number_of_banquet:String,
    val owner_id:String,
    val hotel_profile_photo:String,
    val Cover_photo:String,
    val token:String

){

}
