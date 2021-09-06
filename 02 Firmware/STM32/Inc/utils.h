#ifndef _UTILS_H
#define _UTILS_H

#include "main.h"

void DWT_Init(void){
	if(!(CoreDebug->DEMCR & CoreDebug_DEMCR_TRCENA_Msk)){
		CoreDebug->DEMCR |= CoreDebug_DEMCR_TRCENA_Msk;
		DWT->CYCCNT = 0;
		DWT->CTRL |= DWT_CTRL_CYCCNTENA_Msk;
	}
}

void delay_us(uint64_t us){
	uint32_t startTick = DWT->CYCCNT;
	uint32_t delayTicks = us * (SystemCoreClock / 1000000);
	while(DWT->CYCCNT - startTick < delayTicks);
}

void delay_ms(uint16_t ms){
	while(ms--){
		delay_us(1000);
	}
}

uint64_t get_micros(void){
	return DWT->CYCCNT / (SystemCoreClock / 1000000);
}

uint32_t get_millis(void){
	return DWT->CYCCNT / (SystemCoreClock / 1000);
}

int indexOf(char* s, char* t){
	for(int i = 0; s[i]; i++){
		if(s[i] == t[0]){
			uint8_t flag = 1;
			for(int j = 1; j < t[j]; j++){
				if(s[i + j] != t[j]){
					flag = 0;
					break;
				}
			}
			if(flag) return i;
		}
	}
	return -1;
}
#endif
