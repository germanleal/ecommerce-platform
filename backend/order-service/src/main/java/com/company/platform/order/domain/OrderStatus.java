package com.company.platform.order.domain;
public enum OrderStatus {DRAFT,PENDING,PENDING_CONFIRMATION,CONFIRMED,PROCESSING,COMPLETED,CANCELLED,REJECTED,EXPIRED; public boolean terminal(){return this==COMPLETED||this==CANCELLED||this==REJECTED||this==EXPIRED;} public boolean editable(){return this==DRAFT;}}
