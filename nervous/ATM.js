const API_URL = "http://localhost:8080/api";
let currentSessionId = null;
let currentUserId = null;

class ATMSimulator {
    constructor() {
        this.currentMode = 'menu';
        this.selectedAmount = 0;

        this.initializeElements();
        this.attachEventListeners();
        this.setupLoginScreen();
    }

    initializeElements() {
        // Login elements
        this.loginScreen = document.getElementById('loginScreen');
        this.atmScreen = document.getElementById('atmScreen');
        this.userIdInput = document.getElementById('userIdInput');
        this.pinInput = document.getElementById('pinInput');
        this.loginBtn = document.getElementById('loginBtn');

        // ATM elements
        this.welcomeMsg = document.getElementById('welcomeMsg');
        this.displayContent = document.getElementById('displayContent');
        this.balanceDisplay = document.getElementById('balanceDisplay');
        this.amountInput = document.getElementById('amountInput');
        this.message = document.getElementById('message');
        this.loggedUser = document.getElementById('loggedUser');

        this.checkBalanceBtn = document.getElementById('checkBalanceBtn');
        this.depositBtn = document.getElementById('depositBtn');
        this.withdrawBtn = document.getElementById('withdrawBtn');
        this.logoutBtn = document.getElementById('logoutBtn');
        this.confirmBtn = document.getElementById('confirmBtn');
        this.cancelBtn = document.getElementById('cancelBtn');
        this.quickBtns = document.querySelectorAll('.quick-btn');
    }

    attachEventListeners() {
        // Login events
        this.loginBtn.addEventListener('click', () => this.handleLogin());
        this.userIdInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') this.pinInput.focus();
        });
        this.pinInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') this.handleLogin();
        });

        // ATM events
        this.checkBalanceBtn.addEventListener('click', () => this.checkBalance());
        this.depositBtn.addEventListener('click', () => this.startDeposit());
        this.withdrawBtn.addEventListener('click', () => this.startWithdraw());
        this.logoutBtn.addEventListener('click', () => this.handleLogout());
        this.confirmBtn.addEventListener('click', () => this.confirmTransaction());
        this.cancelBtn.addEventListener('click', () => this.cancelTransaction());

        this.amountInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') this.confirmTransaction();
        });

        this.quickBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                const amount = parseInt(btn.getAttribute('data-amount'));
                this.amountInput.value = amount;
                this.selectedAmount = amount;
                this.confirmTransaction();
            });
        });
    }

    setupLoginScreen() {
        this.loginScreen.style.display = 'flex';
        this.atmScreen.style.display = 'none';
    }

    async handleLogin() {
        const userId = this.userIdInput.value.trim();
        const pin = this.pinInput.value.trim();

        if (!userId || !pin) {
            alert('Please enter both User ID and PIN');
            return;
        }

        try {
            const response = await fetch(`${API_URL}/authenticate`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ userId, pin })
            });

            const data = await response.json();

            if (data.success) {
                currentSessionId = data.sessionId;
                currentUserId = data.userId;
                this.loginScreen.style.display = 'none';
                this.atmScreen.style.display = 'block';
                this.loggedUser.textContent = `Logged in as: ${currentUserId}`;
                this.updateDisplay();
                this.resetToMenu();
            } else {
                alert(data.error || 'Login failed');
            }
        } catch (error) {
            console.error('Login error:', error);
            alert('Error connecting to server. Make sure the ATM Server is running on port 8080.');
        }
    }

    async updateDisplay() {
        if (!currentSessionId) return;

        try {
            const response = await fetch(`${API_URL}/balance?sessionId=${currentSessionId}`);
            const data = await response.json();

            if (data.success) {
                this.balanceDisplay.textContent = `Balance: UgShs ${this.formatCurrency(data.balance)}`;
            }
        } catch (error) {
            console.error('Error fetching balance:', error);
        }
    }

    formatCurrency(amount) {
        return amount.toLocaleString('en-UG', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }

    clearMessage() {
        this.message.textContent = '';
        this.message.className = 'message';
    }

    showMessage(text, type = 'error') {
        this.message.textContent = text;
        this.message.className = `message ${type}`;
    }

    resetToMenu() {
        this.currentMode = 'menu';
        this.amountInput.style.display = 'none';
        this.confirmBtn.style.display = 'none';
        this.cancelBtn.style.display = 'none';
        this.quickBtns.forEach(btn => btn.classList.remove('hidden'));
        this.displayContent.innerHTML = '<p>Select a Transaction</p>';
        this.clearMessage();
        this.updateDisplay();
    }

    async checkBalance() {
        if (!currentSessionId) return;

        try {
            const response = await fetch(`${API_URL}/balance?sessionId=${currentSessionId}`);
            const data = await response.json();

            if (data.success) {
                this.currentMode = 'menu';
                this.displayContent.innerHTML = `
                    <div style="text-align: center;">
                        <p style="font-size: 14px; margin-bottom: 10px;">YOUR CURRENT BALANCE</p>
                        <p style="font-size: 24px; color: #00ff88; font-weight: bold;">UgShs ${this.formatCurrency(data.balance)}</p>
                    </div>
                `;
                this.showMessage('Press any button to continue', 'info');

                setTimeout(() => {
                    this.resetToMenu();
                }, 3000);
            }
        } catch (error) {
            this.showMessage('Error fetching balance', 'error');
        }
    }

    startDeposit() {
        this.currentMode = 'deposit';
        this.selectedAmount = 0;
        this.displayContent.innerHTML = '<p>Enter amount to deposit</p>';
        this.amountInput.style.display = 'block';
        this.amountInput.value = '';
        this.amountInput.placeholder = 'Enter deposit amount';
        this.confirmBtn.style.display = 'block';
        this.cancelBtn.style.display = 'block';
        this.quickBtns.forEach(btn => btn.classList.remove('hidden'));
        this.clearMessage();
        this.amountInput.focus();
    }

    startWithdraw() {
        this.currentMode = 'withdraw';
        this.selectedAmount = 0;
        this.displayContent.innerHTML = '<p>Enter amount to withdraw</p>';
        this.amountInput.style.display = 'block';
        this.amountInput.value = '';
        this.amountInput.placeholder = 'Enter withdrawal amount';
        this.confirmBtn.style.display = 'block';
        this.cancelBtn.style.display = 'block';
        this.quickBtns.forEach(btn => btn.classList.remove('hidden'));
        this.clearMessage();
        this.amountInput.focus();
    }

    confirmTransaction() {
        const amount = parseFloat(this.amountInput.value);

        if (!amount || isNaN(amount)) {
            this.showMessage('Please enter a valid amount', 'error');
            return;
        }

        if (amount <= 0) {
            this.showMessage('Amount must be greater than 0', 'error');
            return;
        }

        if (this.currentMode === 'deposit') {
            this.processDeposit(amount);
        } else if (this.currentMode === 'withdraw') {
            this.processWithdraw(amount);
        }
    }

    async processDeposit(amount) {
        if (!currentSessionId) return;

        try {
            const response = await fetch(`${API_URL}/deposit`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ sessionId: currentSessionId, amount: amount.toString() })
            });

            const data = await response.json();

            if (data.success) {
                this.displayContent.innerHTML = `
                    <div style="text-align: center;">
                        <p style="font-size: 14px; margin-bottom: 10px;">DEPOSIT SUCCESSFUL</p>
                        <p style="font-size: 16px;">Deposited: UgShs ${this.formatCurrency(amount)}</p>
                        <p style="font-size: 14px; margin-top: 10px;">New Balance: UgShs ${this.formatCurrency(data.newBalance)}</p>
                    </div>
                `;
                this.showMessage('Transaction completed', 'success');
                this.amountInput.style.display = 'none';
                this.confirmBtn.style.display = 'none';
                this.cancelBtn.style.display = 'none';
                this.updateDisplay();

                setTimeout(() => {
                    this.resetToMenu();
                }, 3000);
            } else {
                this.showMessage(data.error || 'Deposit failed', 'error');
            }
        } catch (error) {
            console.error('Deposit error:', error);
            this.showMessage('Error processing deposit', 'error');
        }
    }

    async processWithdraw(amount) {
        if (!currentSessionId) return;

        try {
            const response = await fetch(`${API_URL}/withdraw`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ sessionId: currentSessionId, amount: amount.toString() })
            });

            const data = await response.json();

            if (data.success) {
                this.displayContent.innerHTML = `
                    <div style="text-align: center;">
                        <p style="font-size: 14px; margin-bottom: 10px;">WITHDRAWAL SUCCESSFUL</p>
                        <p style="font-size: 16px;">Withdrawn: UgShs ${this.formatCurrency(amount)}</p>
                        <p style="font-size: 14px; margin-top: 10px;">New Balance: UgShs ${this.formatCurrency(data.newBalance)}</p>
                    </div>
                `;
                this.showMessage('Take your cash', 'success');
                this.amountInput.style.display = 'none';
                this.confirmBtn.style.display = 'none';
                this.cancelBtn.style.display = 'none';
                this.updateDisplay();

                setTimeout(() => {
                    this.resetToMenu();
                }, 3000);
            } else {
                this.showMessage(data.error || 'Withdrawal failed', 'error');
            }
        } catch (error) {
            console.error('Withdraw error:', error);
            this.showMessage('Error processing withdrawal', 'error');
        }
    }

    cancelTransaction() {
        this.resetToMenu();
        this.showMessage('Transaction cancelled', 'info');
    }

    async handleLogout() {
        if (!currentSessionId) return;

        try {
            await fetch(`${API_URL}/logout`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ sessionId: currentSessionId })
            });

            currentSessionId = null;
            currentUserId = null;

            this.displayContent.innerHTML = `
                <div style="text-align: center;">
                    <p style="font-size: 16px; margin-bottom: 10px;">THANK YOU!</p>
                    <p style="font-size: 12px; color: #00a8cc;">Please take your card</p>
                </div>
            `;
            this.showMessage('Logged out successfully', 'success');

            setTimeout(() => {
                this.atmScreen.style.display = 'none';
                this.loginScreen.style.display = 'flex';
                this.userIdInput.value = '';
                this.pinInput.value = '';
                this.userIdInput.focus();
            }, 2000);
        } catch (error) {
            console.error('Logout error:', error);
        }
    }
}

// Initialize ATM when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new ATMSimulator();
    document.getElementById('userIdInput').focus();
});
