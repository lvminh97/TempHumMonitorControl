/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.h
  * @brief          : Header for main.c file.
  *                   This file contains the common defines of the application.
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

/* Define to prevent recursive inclusion -------------------------------------*/
#ifndef __MAIN_H
#define __MAIN_H

#ifdef __cplusplus
extern "C" {
#endif

/* Includes ------------------------------------------------------------------*/
#include "stm32f1xx_hal.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Exported types ------------------------------------------------------------*/
/* USER CODE BEGIN ET */

/* USER CODE END ET */

/* Exported constants --------------------------------------------------------*/
/* USER CODE BEGIN EC */

/* USER CODE END EC */

/* Exported macro ------------------------------------------------------------*/
/* USER CODE BEGIN EM */

/* USER CODE END EM */

void HAL_TIM_MspPostInit(TIM_HandleTypeDef *htim);

/* Exported functions prototypes ---------------------------------------------*/
void Error_Handler(void);

/* USER CODE BEGIN EFP */

/* USER CODE END EFP */

/* Private defines -----------------------------------------------------------*/
#define LIGHT_Pin GPIO_PIN_0
#define LIGHT_GPIO_Port GPIOA
#define MOTOR_Pin GPIO_PIN_1
#define MOTOR_GPIO_Port GPIOA
#define LED_OK_Pin GPIO_PIN_5
#define LED_OK_GPIO_Port GPIOA
#define FAN_Pin GPIO_PIN_6
#define FAN_GPIO_Port GPIOA
#define MIST_Pin GPIO_PIN_7
#define MIST_GPIO_Port GPIOA
#define LED_ERROR_Pin GPIO_PIN_0
#define LED_ERROR_GPIO_Port GPIOB
#define LED_CONFIG_Pin GPIO_PIN_2
#define LED_CONFIG_GPIO_Port GPIOB
#define E_RST_Pin GPIO_PIN_14
#define E_RST_GPIO_Port GPIOB
#define CONFIG_Pin GPIO_PIN_8
#define CONFIG_GPIO_Port GPIOA
#define DHT11_Pin GPIO_PIN_6
#define DHT11_GPIO_Port GPIOB
/* USER CODE BEGIN Private defines */
#define LED_ERROR(status) HAL_GPIO_WritePin(LED_ERROR_GPIO_Port, LED_ERROR_Pin, !status)
#define LED_OK(status) HAL_GPIO_WritePin(LED_OK_GPIO_Port, LED_OK_Pin, !status)
#define LED_CONFIG(status) HAL_GPIO_WritePin(LED_CONFIG_GPIO_Port, LED_CONFIG_Pin, !status)

#define ESP_RST(status) HAL_GPIO_WritePin(E_RST_GPIO_Port, E_RST_Pin, !status)
#define ESP_CONFIG HAL_GPIO_ReadPin(CONFIG_GPIO_Port, CONFIG_Pin)

#define FAN(status) HAL_GPIO_WritePin(FAN_GPIO_Port, FAN_Pin, status)
/* USER CODE END Private defines */

#ifdef __cplusplus
}
#endif

#endif /* __MAIN_H */

/************************ (C) COPYRIGHT STMicroelectronics *****END OF FILE****/
