#!/usr/bin/env bash
set -eu

topics="tenant.events user.events marketplace.events commerce.events checkout.events order.events payment.events inventory.events fulfillment.events analytics.events integration.events"
for topic in $topics; do
  kafka-topics --bootstrap-server kafka:29092 --create --if-not-exists --topic "$topic" --partitions 3 --replication-factor 1
done
