/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * <h2><center>&copy; Copyright (c) 2021 STMicroelectronics.
  * All rights reserved.</center></h2>
  *
  * This software component is licensed by ST under BSD 3-Clause license,
  * the "License"; You may not use this file except in compliance with the
  * License. You may obtain a copy of the License at:
  *                        opensource.org/licenses/BSD-3-Clause
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */
#include "string.h"
#include "utils.h"
/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/
ADC_HandleTypeDef hadc1;
DMA_HandleTypeDef hdma_adc1;

TIM_HandleTypeDef htim2;

UART_HandleTypeDef huart1;

/* USER CODE BEGIN PV */
uint32_t adc_buff[1];
uint8_t rx_data[2], rx_buf[150];
uint16_t rx_buf_id;

uint8_t temp, humi;
uint16_t lux;
uint16_t sum_temp, sum_humi, sum_lux, count;

// flag
uint8_t fan_sts, mist_sts, servo_sts;
uint8_t mist_pulse;
uint8_t hasControl;
uint8_t data_ready;
uint8_t configMode, nextConfigMode;
uint8_t wifiConnected;

uint32_t svc_0_5sec;
uint32_t svc_1sec;
uint32_t svc_5sec;
uint32_t svc_100us;
/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
static void MX_DMA_Init(void);
static void MX_ADC1_Init(void);
static void MX_USART1_UART_Init(void);
static void MX_TIM2_Init(void);
/* USER CODE BEGIN PFP */
void Init();
void MainLoopService();
void Set_Pin_Output(GPIO_TypeDef* port, uint16_t pin);
void Set_Pin_Input(GPIO_TypeDef* port, uint16_t pin);
uint8_t DHT11_Read(uint8_t* temp, uint8_t* humi);
uint16_t Get_Lux();
void HAL_UART_RxCpltCallback(UART_HandleTypeDef*);
void SendCmd(char *cmd, int timeout);
/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */

/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  MX_DMA_Init();
  MX_ADC1_Init();
  MX_USART1_UART_Init();
  MX_TIM2_Init();
  /* USER CODE BEGIN 2 */
	Init();
  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1)
  {
		MainLoopService();
    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};
  RCC_PeriphCLKInitTypeDef PeriphClkInit = {0};

  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSE;
  RCC_OscInitStruct.HSEState = RCC_HSE_ON;
  RCC_OscInitStruct.HSEPredivValue = RCC_HSE_PREDIV_DIV1;
  RCC_OscInitStruct.HSIState = RCC_HSI_ON;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_ON;
  RCC_OscInitStruct.PLL.PLLSource = RCC_PLLSOURCE_HSE;
  RCC_OscInitStruct.PLL.PLLMUL = RCC_PLL_MUL9;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }
  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_PLLCLK;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV2;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV1;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_2) != HAL_OK)
  {
    Error_Handler();
  }
  PeriphClkInit.PeriphClockSelection = RCC_PERIPHCLK_ADC;
  PeriphClkInit.AdcClockSelection = RCC_ADCPCLK2_DIV6;
  if (HAL_RCCEx_PeriphCLKConfig(&PeriphClkInit) != HAL_OK)
  {
    Error_Handler();
  }
}

/**
  * @brief ADC1 Initialization Function
  * @param None
  * @retval None
  */
static void MX_ADC1_Init(void)
{

  /* USER CODE BEGIN ADC1_Init 0 */

  /* USER CODE END ADC1_Init 0 */

  ADC_ChannelConfTypeDef sConfig = {0};

  /* USER CODE BEGIN ADC1_Init 1 */

  /* USER CODE END ADC1_Init 1 */
  /** Common config
  */
  hadc1.Instance = ADC1;
  hadc1.Init.ScanConvMode = ADC_SCAN_DISABLE;
  hadc1.Init.ContinuousConvMode = ENABLE;
  hadc1.Init.DiscontinuousConvMode = DISABLE;
  hadc1.Init.ExternalTrigConv = ADC_SOFTWARE_START;
  hadc1.Init.DataAlign = ADC_DATAALIGN_RIGHT;
  hadc1.Init.NbrOfConversion = 1;
  if (HAL_ADC_Init(&hadc1) != HAL_OK)
  {
    Error_Handler();
  }
  /** Configure Regular Channel
  */
  sConfig.Channel = ADC_CHANNEL_0;
  sConfig.Rank = ADC_REGULAR_RANK_1;
  sConfig.SamplingTime = ADC_SAMPLETIME_239CYCLES_5;
  if (HAL_ADC_ConfigChannel(&hadc1, &sConfig) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN ADC1_Init 2 */

  /* USER CODE END ADC1_Init 2 */

}

/**
  * @brief TIM2 Initialization Function
  * @param None
  * @retval None
  */
static void MX_TIM2_Init(void)
{

  /* USER CODE BEGIN TIM2_Init 0 */

  /* USER CODE END TIM2_Init 0 */

  TIM_MasterConfigTypeDef sMasterConfig = {0};
  TIM_OC_InitTypeDef sConfigOC = {0};

  /* USER CODE BEGIN TIM2_Init 1 */

  /* USER CODE END TIM2_Init 1 */
  htim2.Instance = TIM2;
  htim2.Init.Prescaler = 1440 - 1;
  htim2.Init.CounterMode = TIM_COUNTERMODE_UP;
  htim2.Init.Period = 1000 - 1;
  htim2.Init.ClockDivision = TIM_CLOCKDIVISION_DIV1;
  htim2.Init.AutoReloadPreload = TIM_AUTORELOAD_PRELOAD_DISABLE;
  if (HAL_TIM_PWM_Init(&htim2) != HAL_OK)
  {
    Error_Handler();
  }
  sMasterConfig.MasterOutputTrigger = TIM_TRGO_RESET;
  sMasterConfig.MasterSlaveMode = TIM_MASTERSLAVEMODE_DISABLE;
  if (HAL_TIMEx_MasterConfigSynchronization(&htim2, &sMasterConfig) != HAL_OK)
  {
    Error_Handler();
  }
  sConfigOC.OCMode = TIM_OCMODE_PWM1;
  sConfigOC.Pulse = 0;
  sConfigOC.OCPolarity = TIM_OCPOLARITY_HIGH;
  sConfigOC.OCFastMode = TIM_OCFAST_DISABLE;
  if (HAL_TIM_PWM_ConfigChannel(&htim2, &sConfigOC, TIM_CHANNEL_2) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN TIM2_Init 2 */

  /* USER CODE END TIM2_Init 2 */
  HAL_TIM_MspPostInit(&htim2);

}

/**
  * @brief USART1 Initialization Function
  * @param None
  * @retval None
  */
static void MX_USART1_UART_Init(void)
{

  /* USER CODE BEGIN USART1_Init 0 */

  /* USER CODE END USART1_Init 0 */

  /* USER CODE BEGIN USART1_Init 1 */

  /* USER CODE END USART1_Init 1 */
  huart1.Instance = USART1;
  huart1.Init.BaudRate = 9600;
  huart1.Init.WordLength = UART_WORDLENGTH_8B;
  huart1.Init.StopBits = UART_STOPBITS_1;
  huart1.Init.Parity = UART_PARITY_NONE;
  huart1.Init.Mode = UART_MODE_TX_RX;
  huart1.Init.HwFlowCtl = UART_HWCONTROL_NONE;
  huart1.Init.OverSampling = UART_OVERSAMPLING_16;
  if (HAL_UART_Init(&huart1) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN USART1_Init 2 */

  /* USER CODE END USART1_Init 2 */

}

/**
  * Enable DMA controller clock
  */
static void MX_DMA_Init(void)
{

  /* DMA controller clock enable */
  __HAL_RCC_DMA1_CLK_ENABLE();

  /* DMA interrupt init */
  /* DMA1_Channel1_IRQn interrupt configuration */
  HAL_NVIC_SetPriority(DMA1_Channel1_IRQn, 0, 0);
  HAL_NVIC_EnableIRQ(DMA1_Channel1_IRQn);

}

/**
  * @brief GPIO Initialization Function
  * @param None
  * @retval None
  */
static void MX_GPIO_Init(void)
{
  GPIO_InitTypeDef GPIO_InitStruct = {0};

  /* GPIO Ports Clock Enable */
  __HAL_RCC_GPIOD_CLK_ENABLE();
  __HAL_RCC_GPIOA_CLK_ENABLE();
  __HAL_RCC_GPIOB_CLK_ENABLE();

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOA, LED_OK_Pin|FAN_Pin|MIST_Pin, GPIO_PIN_RESET);

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOB, LED_ERROR_Pin|LED_CONFIG_Pin|E_RST_Pin|DHT11_Pin, GPIO_PIN_RESET);

  /*Configure GPIO pins : LED_OK_Pin FAN_Pin MIST_Pin */
  GPIO_InitStruct.Pin = LED_OK_Pin|FAN_Pin|MIST_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOA, &GPIO_InitStruct);

  /*Configure GPIO pins : LED_ERROR_Pin LED_CONFIG_Pin E_RST_Pin DHT11_Pin */
  GPIO_InitStruct.Pin = LED_ERROR_Pin|LED_CONFIG_Pin|E_RST_Pin|DHT11_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOB, &GPIO_InitStruct);

  /*Configure GPIO pin : CONFIG_Pin */
  GPIO_InitStruct.Pin = CONFIG_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_INPUT;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  HAL_GPIO_Init(CONFIG_GPIO_Port, &GPIO_InitStruct);

}

/* USER CODE BEGIN 4 */
void Init(){
	DWT_Init();
	HAL_ADC_Start_DMA(&hadc1, adc_buff, 1);
	LED_CONFIG(0);
	LED_ERROR(1);
	LED_OK(0);
	// Reset ESP8266
	ESP_RST(1);
	delay_ms(4);
	ESP_RST(0);
	// Init flag value
	data_ready = 0;
	hasControl = 0;
	configMode = 0; 
	nextConfigMode = 0;
	wifiConnected = 0;
	sum_temp = sum_humi = sum_lux = count = 0;
	svc_0_5sec = svc_1sec = svc_5sec = get_millis();
	svc_100us = get_micros();
	HAL_UART_Receive_IT(&huart1, (uint8_t*) rx_data, 1);  // set UART interrupt
	HAL_TIM_PWM_Start(&htim2, TIM_CHANNEL_2);
}

void MainLoopService(){
	if(get_millis() - svc_0_5sec >= 500){
		// Get sensor data
		DHT11_Read(&temp, &humi);
		lux = Get_Lux();
		sum_temp += temp;
		sum_humi += humi;
		sum_lux += lux;
		temp = 0;
		humi = 0;
		lux = 0;
		count++;
		// Check wifi status
		if(0 == wifiConnected){
			SendCmd("{\"cmd\":\"GETWIFISTATUS\"}", 1000);
		}
		svc_0_5sec = get_millis();
	}
	if(get_millis() - svc_1sec >= 1000){
		char tmp[40];
		if(1 == data_ready && sum_temp / count > 0 && sum_humi / count > 0){
			sprintf(tmp, "{\"cmd\":\"SEND\",\"data\":[%d,%d,%d]}", sum_temp / count, sum_humi / count, sum_lux / count);
			data_ready = 0;
			sum_temp = sum_humi = sum_lux = count = 0;
		}
		else 
			strcpy(tmp, "{\"cmd\":\"SEND\"}");
		
		if(0 == configMode && 1 == wifiConnected)
			SendCmd(tmp, 1000);
		
		svc_1sec = get_millis();
	}
	if(get_millis() - svc_5sec >= 5000){
		data_ready = 1;
		svc_5sec = get_millis();
	}
	if(0 == ESP_CONFIG){
		// send CONFIG command to ESP8266 to go to Config mode
		SendCmd("{\"cmd\":\"CONFIG\"}", 1000);
		delay_ms(150);
	}
	if(1 == hasControl){
		// control fan, mist, servo
		FAN(fan_sts);
		SERVO(htim2, servo_sts);
		hasControl = 0;
	}
	if(1 == mist_sts && get_micros() - svc_100us >= 50){
		mist_pulse = !mist_pulse;
		MIST(mist_pulse);
		svc_100us = get_micros();
	}
	if(configMode != nextConfigMode){
		configMode = nextConfigMode;
		if(1 == configMode){
			LED_ERROR(0);
			LED_CONFIG(1);
			LED_OK(0);
		}
		else{
			LED_CONFIG(0);
			if(1 == wifiConnected)
				LED_OK(1);
			else
				LED_ERROR(1);
			// Reset ESP8266 after finished the config
			ESP_RST(1);
			delay_ms(4);
			ESP_RST(0);
		}
	}
}

void Set_Pin_Output(GPIO_TypeDef* port, uint16_t pin){
	GPIO_InitTypeDef initStruct = {0};
	initStruct.Pin = pin;
	initStruct.Mode = GPIO_MODE_OUTPUT_PP;
	initStruct.Speed = GPIO_SPEED_FREQ_LOW;
	HAL_GPIO_Init(port, &initStruct);
}

void Set_Pin_Input(GPIO_TypeDef* port, uint16_t pin){
	GPIO_InitTypeDef initStruct = {0};
	initStruct.Pin = pin;
	initStruct.Mode = GPIO_MODE_INPUT;
	initStruct.Pull = GPIO_NOPULL;
	HAL_GPIO_Init(port, &initStruct);
}

uint8_t DHT11_Read(uint8_t* temp, uint8_t* humi){
	uint8_t dht_data[5] = {0};
	uint64_t startTick;
	// Send request signal to DHT11
	Set_Pin_Output(DHT11_GPIO_Port, DHT11_Pin);
	HAL_GPIO_WritePin(DHT11_GPIO_Port, DHT11_Pin, GPIO_PIN_RESET);
	HAL_Delay(18);
	HAL_GPIO_WritePin(DHT11_GPIO_Port, DHT11_Pin, GPIO_PIN_SET);
	delay_us(40);
	// Set as input for receiving the data
	Set_Pin_Input(DHT11_GPIO_Port, DHT11_Pin);
	// The pin is low in 80us
	startTick = get_micros();
	while(!HAL_GPIO_ReadPin(DHT11_GPIO_Port, DHT11_Pin)){
		if(get_micros() - startTick > 100){
			return 255; // timeout
		}
	}
	// The pin is high in 80us
	startTick = get_micros();
	while(HAL_GPIO_ReadPin(DHT11_GPIO_Port, DHT11_Pin)){
		if(get_micros() - startTick > 100){
			return 255; // timeout
		}
	}
	// Read 40 bit response
	for(uint8_t i = 0; i < 5; i++){
		for(uint8_t j = 0; j < 8; j++){
			// Prepare for sending the data from DHT11: the pin is low in 50us
			startTick = get_micros();
			while(!HAL_GPIO_ReadPin(DHT11_GPIO_Port, DHT11_Pin)){
				if(get_micros() - startTick > 60){
					return 255; // timeout
				}
			}
			// HIGH for 26-28us = bit 0 / 70us = bit 1
			startTick = get_micros();
			while(HAL_GPIO_ReadPin(DHT11_GPIO_Port, DHT11_Pin)){
				if(get_micros() - startTick > 100){
					return 255; // timeout
				}
			}
			if(get_micros() - startTick > 40){
				dht_data[i] |= (uint8_t) 1 << (7 - j);
			}
		}
	}
	if(dht_data[0] + dht_data[1] + dht_data[2] + dht_data[3] != dht_data[4]){
		return 0; // checksum error
	}
	*temp = dht_data[2];
	*humi = dht_data[0];
	return 1;
}

uint16_t Get_Lux(){
	double volt = adc_buff[0] / 4095.0 * 3300;
	double res = 3300 * 91 / volt - 91;
	return 500 / res;
}

void HAL_UART_RxCpltCallback(UART_HandleTypeDef* huart){
	if(huart->Instance == USART1){
		rx_buf[rx_buf_id++] = rx_data[0];
		rx_buf[rx_buf_id] = 0;
		if(rx_buf_id > 0 && (rx_data[0] == '\r' || rx_data[0] == '\n')){
			if(indexOf((char*) rx_buf, "control:") != -1) {
				// check fan sts
				if(indexOf((char*) rx_buf, "\"fan\":\"1\"") != -1)
					fan_sts = 1;
				else if(indexOf((char*) rx_buf, "\"fan\":\"0\"") != -1)
					fan_sts = 0;
				// check mist sts
				if(indexOf((char*) rx_buf, "\"mist\":\"1\"") != -1)
					mist_sts = 1;
				else if(indexOf((char*) rx_buf, "\"mist\":\"0\"") != -1)
					mist_sts = 0;
				// check servo sts
				if(indexOf((char*) rx_buf, "\"servo\":\"1\"") != -1)
					servo_sts = 1;
				else if(indexOf((char*) rx_buf, "\"servo\":\"0\"") != -1)
					servo_sts = 0;
				//
				hasControl = 1;
			}
			else if(indexOf((char*) rx_buf, "message:IN_CONFIG_MODE") != -1){
				nextConfigMode = 1;
			}
			else if(indexOf((char*) rx_buf, "message:END_CONFIG_MODE") != -1){
				nextConfigMode = 0;
			}
			else if(indexOf((char*) rx_buf, "message:WIFI_CONNECTED") != -1){
				wifiConnected = 1;
				LED_ERROR(0);
				LED_OK(1);
			}
			//
			rx_buf[0] = 0;
			rx_buf_id = 0;
		}
		HAL_UART_Receive_IT(&huart1, (uint8_t*) rx_data, 1);
	}
}

void SendCmd(char* cmd, int timeout){
	HAL_UART_Transmit(&huart1, (uint8_t*) cmd, strlen(cmd), timeout);
	HAL_UART_Receive_IT(&huart1, (uint8_t*) rx_data, 1);
}
/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */

  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     tex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */

/************************ (C) COPYRIGHT STMicroelectronics *****END OF FILE****/
