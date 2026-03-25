<script setup lang="ts">
import { ref } from 'vue'
import { getLoanDecision, type LoanRequest, type LoanDecision } from '../services/api'

const personalCode = ref('49002010998')
const amount = ref(2000)
const period = ref(12)

const loading = ref(false)
const error = ref<string | null>(null)
const decision = ref<LoanDecision | null>(null)

async function submitForm() {
  loading.value = true
  error.value = null
  decision.value = null

  const request: LoanRequest = {
    personalCode: personalCode.value,
    amount: amount.value,
    period: period.value
  }

  try {
    decision.value = await getLoanDecision(request)
  } catch (e: any) {
    error.value = e.response?.data?.error ?? 'Something went wrong. Please try again.'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="wrapper">
    <div class="card">
      <h1>Loan Decision</h1>
      <p class="subtitle">Find out if you qualify for a loan</p>

      <div class="field">
        <label>Personal Code</label>
        <select v-model="personalCode">
          <option value="49002010965">49002010965 — has debt</option>
          <option value="49002010976">49002010976 — low modifier</option>
          <option value="49002010987">49002010987 — medium modifier</option>
          <option value="49002010998">49002010998 — high modifier</option>

          <option value="49002019043">49002019043 — very low modifier</option>
          <option value="49002010911">49002010911 — very low modifier</option>
          <option value="49002010922">49002010922 — low-medium modifier</option>
          <option value="49002010933">49002010933 — medium-high modifier</option>
          <option value="49002010944">49002010944 — has debt</option>
          <option value="49002010955">49002010955 — very high modifier</option>
        </select>
      </div>

      <div class="field">
        <label>Amount</label>
        <div class="range-header">
          <span class="range-min">€2000</span>
          <span class="range-value">€{{ amount }}</span>
          <span class="range-max">€10000</span>
        </div>
        <input v-model.number="amount" type="range" :min="2000" :max="10000" step="100" />
      </div>

      <div class="field">
        <label>Period</label>
        <div class="range-header">
          <span class="range-min">12 months</span>
          <span class="range-value">{{ period }} months</span>
          <span class="range-max">60 months</span>
        </div>
        <input v-model.number="period" type="range" :min="12" :max="60" step="1" />
      </div>

      <button :disabled="loading" @click="submitForm">
        {{ loading ? 'Processing...' : 'Get Decision' }}
      </button>

      <div v-if="error" class="error">{{ error }}</div>

      <div v-if="decision" class="result">
        <div v-if="decision.approved" class="approved">
          <p class="result-label">Approved</p>
          <p class="result-detail">Amount: <strong>€{{ decision.amount }}</strong></p>
          <p class="result-detail">Period: <strong>{{ decision.period }} months</strong></p>
        </div>
        <div v-else class="rejected">
          <p>{{ decision.reason ?? 'Your loan application was not approved.' }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.wrapper {
  background-color: #1a1a1a;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.card {
  background-color: #262626;
  border-radius: 16px;
  padding: 40px;
  width: 100%;
  max-width: 420px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

h1 {
  font-size: 24px;
  font-weight: 600;
  color: #ffffff;
}

.subtitle {
  font-size: 14px;
  color: #a1a1aa;
  margin-top: -16px;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

label {
  font-size: 13px;
  font-weight: 500;
  color: #a1a1aa;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

select {
  background-color: #1f1f1f;
  color: #ffffff;
  border: 1px solid #3f3f46;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 14px;
  font-family: 'DM Sans', sans-serif;
  cursor: pointer;
  outline: none;
}

select:focus {
  border-color: #f59e0b;
}

.range-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  color: #a1a1aa;
}

.range-value {
  color: #f59e0b;
  font-weight: 600;
  font-size: 15px;
}

input[type='range'] {
  -webkit-appearance: none;
  width: 100%;
  height: 4px;
  border-radius: 2px;
  background: #3f3f46;
  outline: none;
  cursor: pointer;
}

input[type='range']::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: #3f3f46;
  cursor: pointer;
  transition: transform 0.15s ease;
}

input[type='range']::-webkit-slider-thumb:hover {
  transform: scale(1.2);
}

button {
  padding: 14px;
  font-size: 15px;
  font-weight: 600;
  font-family: 'DM Sans', sans-serif;
  cursor: pointer;
  border: none;
  border-radius: 10px;
  background-color: #f59e0b;
  color: #1a1a1a;
  transition: opacity 0.2s ease;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

button:not(:disabled):hover {
  opacity: 0.85;
}

.error {
  color: #f87171;
  font-size: 14px;
}

.result {
  border-radius: 10px;
  padding: 20px;
  background-color: #1f1f1f;
}

.result-label {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
}

.result-detail {
  font-size: 14px;
  color: #a1a1aa;
}

.approved .result-label {
  color: #4ade80;
}

.rejected .result-label {
  color: #f87171;
}
</style>
