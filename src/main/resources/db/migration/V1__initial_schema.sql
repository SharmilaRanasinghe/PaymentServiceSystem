-- src/main/resources/db/migration/V1__initial_schema.sql

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create merchants table
CREATE TABLE merchants (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                           phone VARCHAR(20),
                           address VARCHAR(500),
                           business_registration_number VARCHAR(100),
                           tax_id VARCHAR(100),
                           balance DECIMAL(19,4) DEFAULT 0.0000,
                           credit_limit DECIMAL(19,4) DEFAULT 0.0000,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           version BIGINT DEFAULT 0,

    -- Indexes for performance
                           CONSTRAINT chk_status CHECK (status IN ('PENDING', 'ACTIVE', 'SUSPENDED'))
);

-- Create indexes for merchants
CREATE INDEX idx_merchants_status ON merchants(status);
CREATE INDEX idx_merchants_email ON merchants(email);
CREATE INDEX idx_merchants_created_at ON merchants(created_at DESC);
CREATE INDEX idx_merchants_updated_at ON merchants(updated_at DESC);

-- Create payment_transactions table
CREATE TABLE payment_transactions (
                                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                      merchant_id UUID NOT NULL,
                                      amount DECIMAL(19,4) NOT NULL,
                                      currency CHAR(3) NOT NULL,
                                      payment_method VARCHAR(50) NOT NULL,
                                      status VARCHAR(50) NOT NULL DEFAULT 'INITIATED',
                                      description VARCHAR(255),
                                      customer_email VARCHAR(255),
                                      customer_reference VARCHAR(100),
                                      order_id VARCHAR(100),
                                      gateway_reference VARCHAR(100),
                                      authorization_code VARCHAR(50),
                                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      settled_at TIMESTAMP,
                                      failure_reason VARCHAR(500),
                                      metadata JSONB,
                                      version BIGINT DEFAULT 0,

    -- Foreign key constraint
                                      CONSTRAINT fk_payment_merchant
                                          FOREIGN KEY (merchant_id)
                                              REFERENCES merchants(id)
                                              ON DELETE CASCADE,

    -- Check constraints
                                      CONSTRAINT chk_amount CHECK (amount > 0),
                                      CONSTRAINT chk_currency CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT chk_payment_method CHECK (payment_method IN ('CARD', 'WALLET', 'BANK_TRANSFER', 'CASH_ON_DELIVERY', 'MOBILE_MONEY')),
    CONSTRAINT chk_payment_status CHECK (status IN ('INIT', 'AUTHORIZED', 'SETTLED', 'FAILED', 'REFUNDED'))
);

-- Create indexes for payment_transactions
CREATE INDEX idx_payments_merchant_id ON payment_transactions(merchant_id);
CREATE INDEX idx_payments_status ON payment_transactions(status);
CREATE INDEX idx_payments_created_at ON payment_transactions(created_at DESC);
CREATE INDEX idx_payments_order_id ON payment_transactions(order_id);
CREATE INDEX idx_payments_customer_email ON payment_transactions(customer_email);
CREATE INDEX idx_payments_payment_method ON payment_transactions(payment_method);
CREATE INDEX idx_payments_currency ON payment_transactions(currency);
CREATE INDEX idx_payments_gateway_ref ON payment_transactions(gateway_reference);
CREATE INDEX idx_payments_metadata ON payment_transactions USING GIN (metadata);

-- Create composite indexes for common queries
CREATE INDEX idx_payments_merchant_status ON payment_transactions(merchant_id, status);
CREATE INDEX idx_payments_date_status ON payment_transactions(created_at, status);
CREATE INDEX idx_payments_amount_currency ON payment_transactions(amount, currency);

-- Create refunds table (for future enhancement)
CREATE TABLE refunds (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         payment_id UUID NOT NULL,
                         merchant_id UUID NOT NULL,
                         amount DECIMAL(19,4) NOT NULL,
                         reason VARCHAR(500),
                         status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         processed_at TIMESTAMP,
                         gateway_reference VARCHAR(100),

                         CONSTRAINT fk_refund_payment
                             FOREIGN KEY (payment_id)
                                 REFERENCES payment_transactions(id),

                         CONSTRAINT fk_refund_merchant
                             FOREIGN KEY (merchant_id)
                                 REFERENCES merchants(id),

                         CONSTRAINT chk_refund_status CHECK (status IN ('PENDING', 'PROCESSED', 'FAILED')),
                         CONSTRAINT chk_refund_amount CHECK (amount > 0)
);

-- Create indexes for refunds
CREATE INDEX idx_refunds_payment_id ON refunds(payment_id);
CREATE INDEX idx_refunds_merchant_id ON refunds(merchant_id);
CREATE INDEX idx_refunds_status ON refunds(status);