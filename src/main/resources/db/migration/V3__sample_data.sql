-- src/main/resources/db/migration/V3__sample_data.sql
-- Optional: Insert sample data for testing

INSERT INTO merchants (id, name, email, status, created_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Demo Merchant 1', 'demo1@merchant.com', 'ACTIVE', CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222222', 'Demo Merchant 2', 'demo2@merchant.com', 'PENDING', CURRENT_TIMESTAMP)
    ON CONFLICT (email) DO NOTHING;