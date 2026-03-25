import axios from 'axios'

const API_URL = 'http://localhost:8080/api/loan'

export interface LoanRequest {
    personalCode: string
    amount: number
    period: number
}

export interface LoanDecision {
    approved: boolean
    amount: number
    period: number
    reason: string | null
}

export async function getLoanDecision(request: LoanRequest): Promise<LoanDecision> {
    const response = await axios.post<LoanDecision>(`${API_URL}/decision`, request)
    return response.data
}
