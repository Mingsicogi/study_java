"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
Object.defineProperty(exports, "__esModule", { value: true });
/* tslint:disable max-line-length */
var axios_1 = require("axios");
var sinon_1 = require("sinon");
var game_server_service_1 = require("@/entities/game-server/game-server.service");
var game_server_model_1 = require("@/shared/model/game-server.model");
var error = {
    response: {
        status: null,
        data: {
            type: null,
        },
    },
};
var axiosStub = {
    get: sinon_1.default.stub(axios_1.default, 'get'),
    post: sinon_1.default.stub(axios_1.default, 'post'),
    put: sinon_1.default.stub(axios_1.default, 'put'),
    patch: sinon_1.default.stub(axios_1.default, 'patch'),
    delete: sinon_1.default.stub(axios_1.default, 'delete'),
};
describe('Service Tests', function () {
    describe('GameServer Service', function () {
        var service;
        var elemDefault;
        beforeEach(function () {
            service = new game_server_service_1.default();
            elemDefault = new game_server_model_1.GameServer(123, 'AAAAAAA', 'AAAAAAA');
        });
        describe('Service methods', function () {
            it('should find an element', function () { return __awaiter(void 0, void 0, void 0, function () {
                var returnedFromService;
                return __generator(this, function (_a) {
                    returnedFromService = Object.assign({}, elemDefault);
                    axiosStub.get.resolves({ data: returnedFromService });
                    return [2 /*return*/, service.find(123).then(function (res) {
                            expect(res).toMatchObject(elemDefault);
                        })];
                });
            }); });
            it('should not find an element', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.get.rejects(error);
                    return [2 /*return*/, service
                            .find(123)
                            .then()
                            .catch(function (err) {
                            expect(err).toMatchObject(error);
                        })];
                });
            }); });
            it('should create a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                var returnedFromService, expected;
                return __generator(this, function (_a) {
                    returnedFromService = Object.assign({
                        id: 123,
                    }, elemDefault);
                    expected = Object.assign({}, returnedFromService);
                    axiosStub.post.resolves({ data: returnedFromService });
                    return [2 /*return*/, service.create({}).then(function (res) {
                            expect(res).toMatchObject(expected);
                        })];
                });
            }); });
            it('should not create a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.post.rejects(error);
                    return [2 /*return*/, service
                            .create({})
                            .then()
                            .catch(function (err) {
                            expect(err).toMatchObject(error);
                        })];
                });
            }); });
            it('should update a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                var returnedFromService, expected;
                return __generator(this, function (_a) {
                    returnedFromService = Object.assign({
                        gameServerId: 'BBBBBB',
                        region: 'BBBBBB',
                    }, elemDefault);
                    expected = Object.assign({}, returnedFromService);
                    axiosStub.put.resolves({ data: returnedFromService });
                    return [2 /*return*/, service.update(expected).then(function (res) {
                            expect(res).toMatchObject(expected);
                        })];
                });
            }); });
            it('should not update a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.put.rejects(error);
                    return [2 /*return*/, service
                            .update({})
                            .then()
                            .catch(function (err) {
                            expect(err).toMatchObject(error);
                        })];
                });
            }); });
            it('should partial update a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                var patchObject, returnedFromService, expected;
                return __generator(this, function (_a) {
                    patchObject = Object.assign({}, new game_server_model_1.GameServer());
                    returnedFromService = Object.assign(patchObject, elemDefault);
                    expected = Object.assign({}, returnedFromService);
                    axiosStub.patch.resolves({ data: returnedFromService });
                    return [2 /*return*/, service.partialUpdate(patchObject).then(function (res) {
                            expect(res).toMatchObject(expected);
                        })];
                });
            }); });
            it('should not partial update a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.patch.rejects(error);
                    return [2 /*return*/, service
                            .partialUpdate({})
                            .then()
                            .catch(function (err) {
                            expect(err).toMatchObject(error);
                        })];
                });
            }); });
            it('should return a list of GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                var returnedFromService, expected;
                return __generator(this, function (_a) {
                    returnedFromService = Object.assign({
                        gameServerId: 'BBBBBB',
                        region: 'BBBBBB',
                    }, elemDefault);
                    expected = Object.assign({}, returnedFromService);
                    axiosStub.get.resolves([returnedFromService]);
                    return [2 /*return*/, service.retrieve().then(function (res) {
                            expect(res).toContainEqual(expected);
                        })];
                });
            }); });
            it('should not return a list of GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.get.rejects(error);
                    return [2 /*return*/, service
                            .retrieve()
                            .then()
                            .catch(function (err) {
                            expect(err).toMatchObject(error);
                        })];
                });
            }); });
            it('should delete a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.delete.resolves({ ok: true });
                    return [2 /*return*/, service.delete(123).then(function (res) {
                            expect(res.ok).toBeTruthy();
                        })];
                });
            }); });
            it('should not delete a GameServer', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    axiosStub.delete.rejects(error);
                    return [2 /*return*/, service
                            .delete(123)
                            .then()
                            .catch(function (err) {
                            expect(err).toMatchObject(error);
                        })];
                });
            }); });
        });
    });
});
