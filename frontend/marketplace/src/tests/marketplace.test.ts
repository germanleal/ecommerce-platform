import { describe, expect, it } from 'vitest';
import { apiMessage } from '../services/api/client';

describe('marketplace error handling', () => {
  it('returns a safe message for unexpected failures', () => {
    expect(apiMessage(new Error('database details'))).toBe('Something went wrong. Please try again.');
  });
});
