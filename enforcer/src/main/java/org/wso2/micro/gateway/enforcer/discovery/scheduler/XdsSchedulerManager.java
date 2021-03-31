/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.micro.gateway.enforcer.discovery.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.micro.gateway.enforcer.config.EnvVarConfig;
import org.wso2.micro.gateway.enforcer.discovery.ApiDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.ApiListDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.ApplicationDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.ApplicationKeyMappingDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.KeyManagerDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.RevokedTokenDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.SubscriptionDiscoveryClient;
import org.wso2.micro.gateway.enforcer.discovery.ThrottleDataDiscoveryClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Manages all the scheduling tasks that runs for retrying discovery requests.
 */
public class XdsSchedulerManager {

    private static final Logger log = LogManager.getLogger(XdsSchedulerManager.class);
    private static int retryPeriod;
    private static volatile XdsSchedulerManager instance;

    private static ScheduledExecutorService discoveryClientScheduler;
    private ScheduledFuture<?> apiDiscoveryScheduledFuture;
    private ScheduledFuture<?> apiDiscoveryListScheduledFuture;
    private ScheduledFuture<?> applicationDiscoveryScheduledFuture;
    private ScheduledFuture<?> applicationKeyMappingDiscoveryScheduledFuture;
    private ScheduledFuture<?> keyManagerDiscoveryScheduledFuture;
    private ScheduledFuture<?> revokedTokenDiscoveryScheduledFuture;
    private ScheduledFuture<?> subscriptionDiscoveryScheduledFuture;
    private ScheduledFuture<?> throttleDataDiscoveryScheduledFuture;

    public static XdsSchedulerManager getInstance() {
        if (instance == null) {
            synchronized (XdsSchedulerManager.class) {
                if (instance == null) {
                    instance = new XdsSchedulerManager();
                    discoveryClientScheduler = Executors.newSingleThreadScheduledExecutor();
                    retryPeriod = Integer.parseInt(EnvVarConfig.getInstance().getXdsRetryPeriod());
                }
            }
        }
        return instance;
    }

    public synchronized void startAPIDiscoveryScheduling() {
        if (apiDiscoveryScheduledFuture == null || apiDiscoveryScheduledFuture.isDone()) {
            apiDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(ApiDiscoveryClient.getInstance(), 1, retryPeriod, TimeUnit.SECONDS);
        }
    }

    public synchronized void stopAPIDiscoveryScheduling() {
        if (apiDiscoveryScheduledFuture != null && !apiDiscoveryScheduledFuture.isDone()) {
            apiDiscoveryScheduledFuture.cancel(false);
        }
    }

    public synchronized void startAPIListDiscoveryScheduling() {
        if (apiDiscoveryListScheduledFuture == null || apiDiscoveryListScheduledFuture.isDone()) {
            apiDiscoveryListScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(ApiListDiscoveryClient.getInstance(), 1, retryPeriod, TimeUnit.SECONDS);
        }
    }

    public synchronized void stopAPIListDiscoveryScheduling() {
        if (apiDiscoveryListScheduledFuture != null && !apiDiscoveryListScheduledFuture.isDone()) {
            apiDiscoveryListScheduledFuture.cancel(false);
        }
    }

    public synchronized void startApplicationDiscoveryScheduling() {
        if (applicationDiscoveryScheduledFuture == null || applicationDiscoveryScheduledFuture.isDone()) {
            applicationDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(ApplicationDiscoveryClient.getInstance(), 1, retryPeriod, TimeUnit.SECONDS);
        }
    }

    public synchronized void stopApplicationDiscoveryScheduling() {
        if (applicationDiscoveryScheduledFuture != null && !applicationDiscoveryScheduledFuture.isDone()) {
            applicationDiscoveryScheduledFuture.cancel(false);
        }
    }

    public synchronized void startApplicationKeyMappingDiscoveryScheduling() {
        if (applicationKeyMappingDiscoveryScheduledFuture == null || applicationKeyMappingDiscoveryScheduledFuture
                .isDone()) {
            applicationKeyMappingDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(ApplicationKeyMappingDiscoveryClient.getInstance(), 1, retryPeriod,
                            TimeUnit.SECONDS);
        }
    }

    public synchronized void stopApplicationKeyMappingDiscoveryScheduling() {
        if (applicationKeyMappingDiscoveryScheduledFuture != null && !applicationKeyMappingDiscoveryScheduledFuture
                .isDone()) {
            applicationKeyMappingDiscoveryScheduledFuture.cancel(false);
        }
    }

    public synchronized void startKeyManagerDiscoveryScheduling() {
        if (keyManagerDiscoveryScheduledFuture == null || keyManagerDiscoveryScheduledFuture.isDone()) {
            keyManagerDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(KeyManagerDiscoveryClient.getInstance(), 1, retryPeriod, TimeUnit.SECONDS);
        }
    }

    public synchronized void stopKeyManagerDiscoveryScheduling() {
        if (keyManagerDiscoveryScheduledFuture != null && !keyManagerDiscoveryScheduledFuture.isDone()) {
            keyManagerDiscoveryScheduledFuture.cancel(false);
        }
    }

    public synchronized void startRevokedTokenDiscoveryScheduling() {
        if (revokedTokenDiscoveryScheduledFuture == null || revokedTokenDiscoveryScheduledFuture.isDone()) {
            revokedTokenDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(RevokedTokenDiscoveryClient.getInstance(), 1, retryPeriod,
                            TimeUnit.SECONDS);
        }
    }

    public synchronized void stopRevokedTokenDiscoveryScheduling() {
        if (revokedTokenDiscoveryScheduledFuture != null && !revokedTokenDiscoveryScheduledFuture.isDone()) {
            revokedTokenDiscoveryScheduledFuture.cancel(false);
        }
    }

    public synchronized void startSubscriptionDiscoveryScheduling() {
        if (subscriptionDiscoveryScheduledFuture == null || subscriptionDiscoveryScheduledFuture.isDone()) {
            subscriptionDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(SubscriptionDiscoveryClient.getInstance(), 1, retryPeriod,
                            TimeUnit.SECONDS);
        }
    }

    public synchronized void stopSubscriptionDiscoveryScheduling() {
        if (subscriptionDiscoveryScheduledFuture != null && !subscriptionDiscoveryScheduledFuture.isDone()) {
            subscriptionDiscoveryScheduledFuture.cancel(false);
        }
    }

    public synchronized void startThrottleDataDiscoveryScheduling() {
        if (throttleDataDiscoveryScheduledFuture == null || throttleDataDiscoveryScheduledFuture.isDone()) {
            throttleDataDiscoveryScheduledFuture = discoveryClientScheduler
                    .scheduleWithFixedDelay(ThrottleDataDiscoveryClient.getInstance(), 1, retryPeriod,
                            TimeUnit.SECONDS);
        }
    }

    public synchronized void stopThrottleDataDiscoveryScheduling() {
        if (throttleDataDiscoveryScheduledFuture != null && !throttleDataDiscoveryScheduledFuture.isDone()) {
            throttleDataDiscoveryScheduledFuture.cancel(false);
        }
    }
}
