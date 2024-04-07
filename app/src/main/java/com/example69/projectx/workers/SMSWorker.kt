package com.example69.projectx.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.provider.Telephony
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import androidx.work.PeriodicWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example69.projectx.MainActivity
import com.example69.projectx.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class SMSWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

//    override fun doWork(): Result {
//        val sharedPreferences = applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        val lastProcessedTime = sharedPreferences.getLong(LAST_PROCESSED_TIME_KEY, 0L)
//        val currentTime = System.currentTimeMillis()
//
//        if (currentTime - lastProcessedTime >= TimeUnit.MINUTES.toMillis(20)) {
//            // Execute the worker's task
//            Log.d(TAG, "Worker started")
//            val lastProcessedMessageTime = getLastProcessedMessageTime()
//            val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.getDefault()) // Define the date format
//            val formattedTime = dateFormat.format(Date(lastProcessedMessageTime)) // Convert timestamp to human-readable format
//            Log.d(TAG, "Last processed message time: $formattedTime")
//            val cursor = queryMessagesFromTimestamp(lastProcessedMessageTime)
//            if (cursor != null) {
//                processMessages(cursor)
//                cursor.close()
//            }
//            Log.d(TAG, "Worker finished")
//            val lastProcessedMessageTime2 = getLastProcessedMessageTime()
//            val formattedTime2 = dateFormat.format(Date(lastProcessedMessageTime2)) // Convert timestamp to human-readable format
//            Log.d(TAG, "Last processed message time AFTER FINISH: $formattedTime2")
//
//            // Save the current time as the last processed time
//            sharedPreferences.edit().putLong(LAST_PROCESSED_TIME_KEY, currentTime).apply()
//        } else {
//            Log.d(TAG, "Worker already executed within the last 20 minutes. Skipping.")
//        }
//
//        // Sleep for 5 seconds after each worker execution
//        try {
//            Thread.sleep(TimeUnit.SECONDS.toMillis(5))
//        } catch (e: InterruptedException) {
//            Log.e(TAG, "Thread sleep interrupted: ${e.message}")
//        }
//
//        return Result.success()
//    }

    override fun doWork(): Result {
        Log.d(TAG, "Worker started")
        val lastProcessedMessageTime = getLastProcessedMessageTime()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.getDefault()) // Define the date format
        val formattedTime = dateFormat.format(Date(lastProcessedMessageTime)) // Convert timestamp to human-readable format
        Log.d(TAG, "Last processed message time: $formattedTime")
        val cursor = queryMessagesFromTimestamp(lastProcessedMessageTime)
        if (cursor != null) {
            processMessages(cursor)
            cursor.close()
        }
        Log.d(TAG, "Worker finished")
        val lastProcessedMessageTime2 = getLastProcessedMessageTime()
        val formattedTime2 = dateFormat.format(Date(lastProcessedMessageTime2)) // Convert timestamp to human-readable format
        Log.d(TAG, "Last processed message time AFTER FINISH: $formattedTime2")
        return Result.success()
    }

//    private fun getLastProcessedMessageTime(): Long {
//        val sharedPreferences = applicationContext.getSharedPreferences(
//            LAST_PROCESSED_MESSAGE_PREFS,
//            Context.MODE_PRIVATE
//        )
//        return sharedPreferences.getLong(LAST_PROCESSED_MESSAGE_TIME_KEY, System.currentTimeMillis() - PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS)
//    }

    private fun getLastProcessedMessageTime(): Long {
        val sharedPreferences = applicationContext.getSharedPreferences(
            LAST_PROCESSED_MESSAGE_PREFS,
            Context.MODE_PRIVATE
        )
        val prefValue = sharedPreferences.getLong(
            LAST_PROCESSED_MESSAGE_TIME_KEY,
            0L
        )
        val minIntervalMillis = TimeUnit.MINUTES.toMillis(20) // 20 minutes in milliseconds
        val minTimestamp = System.currentTimeMillis() - minIntervalMillis
        return maxOf(prefValue, minTimestamp)
    }

    private fun setLastProcessedMessageTime(timestamp: Long) {
        val sharedPreferences = applicationContext.getSharedPreferences(
            LAST_PROCESSED_MESSAGE_PREFS,
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit {
            putLong(LAST_PROCESSED_MESSAGE_TIME_KEY, timestamp)
            apply()
        }
    }

    private fun queryMessagesFromTimestamp(timestamp: Long): Cursor? {
        val selection = "${Telephony.Sms.Inbox.DATE_SENT} > ? AND (${Telephony.Sms.Inbox.BODY} LIKE ? OR ${Telephony.Sms.Inbox.BODY} LIKE ? OR ${Telephony.Sms.Inbox.BODY} LIKE ? OR ${Telephony.Sms.Inbox.BODY} LIKE ? OR ${Telephony.Sms.Inbox.BODY} LIKE ? OR ${Telephony.Sms.Inbox.BODY} LIKE ? OR ${Telephony.Sms.Inbox.BODY} LIKE ?)"
        val selectionArgs = arrayOf(
            timestamp.toString(), "%Rs%", "%INR%", "%credited%", "%debited%", "%debit%", "%credit%"
        )

        return applicationContext.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            null,
            selection,
            selectionArgs,
            "${Telephony.Sms.Inbox.DATE_SENT} DESC"
        )
    }

    private fun processMessages(cursor: Cursor) {
        while (cursor.moveToNext()) {
            val messageId = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox._ID))
            Log.d(TAG, "Processing message with ID: $messageId")
            val sender = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.ADDRESS))
            val messageBody = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.BODY))
            if (!isMessageProcessed(messageId)) {
                val regexPattern = "[0-9,]+\\.[0-9]+"
                val regex = Regex(regexPattern)
                val matchResult = regex.find(messageBody)
                matchResult?.let {
                    val transactionAmount = it.value
                    Log.d(TAG, "Transaction amount found: $transactionAmount")
                    showNotification(sender, messageBody, transactionAmount)
                }
                markMessageAsProcessed(messageId)
            } else {
                Log.d(TAG, "Message with ID $messageId already processed")
            }
            setLastProcessedMessageTime(cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.DATE_SENT)))
        }
    }

    private fun showNotification(sender: String, message: String, amount: String) {
        Log.d(TAG, "Showing notification for message: $message")
        createNotificationChannel()

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Transaction of Rs.$amount detected!")
            .setContentText("You might want to record this transaction from $sender to keep track :)")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationId = (System.currentTimeMillis() % 10000).toInt()

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "SMS Notifications"
            val descriptionText = "Channel for incoming SMS notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun isMessageProcessed(messageId: Long): Boolean {
        val sharedPreferences = applicationContext.getSharedPreferences(
            PROCESSED_MESSAGES_PREFS,
            Context.MODE_PRIVATE
        )
        val processedMessages = sharedPreferences.getStringSet(PROCESSED_MESSAGES_SET_KEY, mutableSetOf()) ?: mutableSetOf()
        return messageId.toString() in processedMessages
    }

    private fun markMessageAsProcessed(messageId: Long) {
        val sharedPreferences = applicationContext.getSharedPreferences(
            PROCESSED_MESSAGES_PREFS,
            Context.MODE_PRIVATE
        )
        val processedMessages = sharedPreferences.getStringSet(PROCESSED_MESSAGES_SET_KEY, mutableSetOf()) ?: mutableSetOf()
        processedMessages.add(messageId.toString())
        sharedPreferences.edit {
            putStringSet(PROCESSED_MESSAGES_SET_KEY, processedMessages)
            apply()
        }
    }

    companion object {
        private const val TAG = "SMSWorker"
        private const val PROCESSED_MESSAGES_PREFS = "processed_messages_prefs"
        private const val PROCESSED_MESSAGES_SET_KEY = "processed_messages_set"
        private const val LAST_PROCESSED_MESSAGE_PREFS = "last_processed_message_prefs"
        private const val LAST_PROCESSED_MESSAGE_TIME_KEY = "last_processed_message_time"
        private const val CHANNEL_ID = "incoming_sms_channel"
        const val WORK_NAME = "SMSWorker"
        private const val PREFS_NAME = "SMSWorkerPrefs"
        private const val LAST_PROCESSED_TIME_KEY = "lastProcessedTime"
    }
}