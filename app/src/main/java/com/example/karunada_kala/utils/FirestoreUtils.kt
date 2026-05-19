package com.example.karunada_kala.utils

import com.example.karunada_kala.model.ArtForm
import com.example.karunada_kala.model.Artisan
import com.example.karunada_kala.model.Event
import com.example.karunada_kala.model.Product
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtils {

    fun seedSampleData() {
        val db = FirebaseFirestore.getInstance()

        // 1. Art Forms
        val artForms = listOf(
            ArtForm("yakshagana", "Yakshagana", "Yakshagana is a traditional theater form that combines dance, music, dialogue, costume, make-up, and stage techniques with a unique style and form. This theater style is mainly found in coastal regions and adjoining areas of Karnataka.", "Coastal Karnataka", "https://www.youtube.com/watch?v=kYv_3M_u9vA", "https://images.unsplash.com/photo-1623150537021-f09dfd708304?q=80&w=1000&auto=format&fit=crop"),
            ArtForm("dollu_kunitha", "Dollu Kunitha", "Dollu Kunitha is a major popular drum dance of Karnataka. It is performed by the Kuruba community. The performance features large drums, synchronized rhythmic movements, and soulful singing.", "North & Central Karnataka", "", "https://images.unsplash.com/photo-1596464716127-f2a82984de30?q=80&w=1000&auto=format&fit=crop"),
            ArtForm("bidriware", "Bidriware", "Bidriware is a metal handicraft from Bidar. It is developed in the 14th century C.E. during the rule of the Bahamani Sultans. The metal used is a blackened alloy of zinc and copper inlaid with sheets of pure silver.", "Bidar", "", "https://images.unsplash.com/photo-1582738411706-bfc8e691d1c2?q=80&w=1000&auto=format&fit=crop"),
            ArtForm("channapatna", "Channapatna Toys", "Traditional wooden toys and dolls manufactured in the town of Channapatna. These are colored using natural vegetable dyes, making them eco-friendly and safe for children.", "Ramanagara", "", "https://images.unsplash.com/photo-1596461404969-9ae70f2830c1?q=80&w=1000&auto=format&fit=crop")
        )
        artForms.forEach { db.collection("artforms").document(it.id).set(it) }

        // 2. Artisans
        val artisans = listOf(
            Artisan("art1", "Shiva Bhat", "Yakshagana", "9123456780", 12.9716, 77.5946, "Bangalore", "Performance", "Veteran Yakshagana artist with 30 years of experience in Tenkutittu style.", "https://i.pravatar.cc/150?u=art1"),
            Artisan("art2", "Manju Hegde", "Bidriware", "9876543210", 17.9116, 77.5177, "Bidar", "Workshop", "Award-winning Bidriware craftsman specializing in silver inlay work.", "https://i.pravatar.cc/150?u=art2"),
            Artisan("art3", "Rajesh Gowda", "Channapatna Toys", "9988776655", 12.6518, 77.2088, "Channapatna", "Seller", "Traditional toy maker using eco-friendly natural dyes.", "https://i.pravatar.cc/150?u=art3")
        )
        artisans.forEach { db.collection("artisans").document(it.id).set(it) }

        // 3. Events
        val events = listOf(
            Event("ev1", "Annual Yakshagana Utsav", "Yakshagana", "2025-05-15", "Mangalore Stadium", 12.9141, 74.8560),
            Event("ev2", "Bidriware Live Workshop", "Bidriware", "2025-06-10", "Bidar Craft Center", 17.9116, 77.5177),
            Event("ev3", "Folk Dance Festival", "Dollu Kunitha", "2025-07-04", "Mysore Palace Ground", 12.3052, 76.6552)
        )
        events.forEach { db.collection("events").document(it.id).set(it) }

        // 4. Products
        val products = listOf(
            Product("prod1", "Bidriware Vase", "Exquisite handcrafted metal vase with intricate silver inlay work from Bidar.", "₹4,500", "https://images.unsplash.com/photo-1614359833899-6e1bd2944e8a?q=80&w=1000&auto=format&fit=crop", "9876543210"),
            Product("prod2", "Ilkal Kasuti Saree", "Pure silk Ilkal saree with traditional Kasuti hand embroidery.", "₹6,500", "https://images.unsplash.com/photo-1610030469983-98e550d6193c?q=80&w=1000&auto=format&fit=crop", "9123456781"),
            Product("prod3", "Channapatna Wooden Train", "Classic hand-turned wooden train set colored with natural dyes.", "₹850", "https://images.unsplash.com/photo-1531651008558-ed1758b27ef5?q=80&w=1000&auto=format&fit=crop", "9988776655")
        )
        products.forEach { db.collection("products").document(it.id).set(it) }
    }
}
