function scrollToTop() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }

        const cards = document.querySelectorAll('.project-card');
        window.addEventListener('scroll', () => {
            const triggerBottom = window.innerHeight / 5 * 4;
            cards.forEach(card => {
                const cardTop = card.getBoundingClientRect().top;
                if(cardTop < triggerBottom){
                    card.style.transform = 'translateY(0)';
                    card.style.opacity = '1';
                    card.style.transition = 'all 0.6s ease-out';
                } else {
                    card.style.transform = 'translateY(50px)';
                    card.style.opacity = '0';
                }
            });
        });