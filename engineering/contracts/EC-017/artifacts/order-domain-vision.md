# Order Domain Vision

## Purpose

Order Management represents the formal commercial commitment created from a customer's shopping intent. It records what was purchased, under which tenant and store, at what commercial conditions, and how the order progresses.

## Vision and objectives

The context provides a durable, auditable order history with explicit lifecycle transitions, immutable commercial snapshots, tenant isolation and event-driven handoff to downstream capabilities.

Objectives: accept a valid purchase request from Commerce, confirm the commercial snapshot, expose order state, preserve traceability, and publish facts for Payments, Inventory, Logistics and Notifications.

## Actors

Customer creates and reviews own orders. Store Manager and Customer Service review and operate orders within their store/tenant scope. Tenant Administrator governs tenant-wide access. Platform Administrator operates the platform without bypassing tenant authorization rules.

## Capabilities

Order creation, confirmation, cancellation, expiration, completion, order history, snapshot preservation, tenant-scoped access and domain-event publication.

## Business rules

An order belongs to exactly one tenant, customer and store. It must contain at least one item. Item quantity is positive. Every item stores a commercial snapshot and never recalculates historical pricing. Only valid lifecycle transitions are allowed. A completed or cancelled order cannot be mutated.

