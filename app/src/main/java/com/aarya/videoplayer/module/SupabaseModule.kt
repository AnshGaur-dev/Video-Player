package com.aarya.videoplayer.module


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.dsl.module


val supabase = createSupabaseClient(
    supabaseUrl = "https://ftlbuuhbymifxtqzczun.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZ0bGJ1dWhieW1pZnh0cXpjenVuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUwMjQzMTgsImV4cCI6MjAzMDYwMDMxOH0.AbUFGd7d50y2cM6cTz0H4uUQmSx9_cZ-nqCwrAyE4oI"
) {
//    install(Auth)
//    defaultSerializer = KotlinXSerializer(Json {})
    install(Postgrest)
    //install other modules
}

//val storageModule = module {
//    single { Storage.Builder(get()).build() }
//}