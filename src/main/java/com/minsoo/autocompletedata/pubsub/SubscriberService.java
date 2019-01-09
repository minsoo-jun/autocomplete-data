package com.minsoo.autocompletedata.pubsub;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PushConfig;
import com.minsoo.autocompletedata.constants.Constants;

public class SubscriberService {

    public static void main(String... args) throws Exception {
        System.out.println("### SubscriberService start:");
        ProjectTopicName topic = ProjectTopicName.of(Constants.PROJECT_ID, Constants.TOPIC_ID);
        ProjectSubscriptionName subscription =
                ProjectSubscriptionName.of(Constants.PROJECT_ID, Constants.SUBSCRIPTION_ID);

        try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
            subscriptionAdminClient.createSubscription(
                    subscription, topic, PushConfig.getDefaultInstance(), 0);
        }

        MessageReceiver receiver =
                new MessageReceiver() {
                    @Override
                    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
                        System.out.println("Received message: " + message.getData().toStringUtf8());
                        consumer.ack();
                    }
                };
        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(subscription, receiver).build();
            subscriber.addListener(
                    new Subscriber.Listener() {
                        @Override
                        public void failed(Subscriber.State from, Throwable failure) {
                            // Handle failure. This is called when the Subscriber encountered a fatal error and is
                            // shutting down.
                            System.err.println(failure);
                        }
                    },
                    MoreExecutors.directExecutor());
            subscriber.startAsync().awaitRunning();

            // In this example, we will pull messages for one minute (60,000ms) then stop.
            // In a real application, this sleep-then-stop is not necessary.
            // Simply call stopAsync().awaitTerminated() when the server is shutting down, etc.
            Thread.sleep(60000);
        } finally {
            if (subscriber != null) {
                subscriber.stopAsync().awaitTerminated();
            }
        }
    }
}
